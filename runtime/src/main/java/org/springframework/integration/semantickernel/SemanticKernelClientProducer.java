package org.springframework.integration.semantickernel;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.microsoft.semantickernel.connectors.ai.openai.util.AzureOpenAISettings;
import com.microsoft.semantickernel.connectors.ai.openai.util.ClientType;
import com.microsoft.semantickernel.connectors.ai.openai.util.OpenAIClientProvider;
import com.microsoft.semantickernel.connectors.ai.openai.util.OpenAISettings;
import com.microsoft.semantickernel.exceptions.ConfigurationException;

@Configuration
public class SemanticKernelClientProducer {

    @Autowired
    SemanticKernelConfiguration semanticKernelConfiguration;

    @Scope("singleton")
    @Bean
    public OpenAIAsyncClient produceOpenAIAsyncClient() throws ConfigurationException {
        
        if (semanticKernelConfiguration.getClient().isEmpty()) {
            // There is no Semantic Kernel configuration at the Spring level (not in the application.properties file, etc.)
            // We should return a default OpenAIAsyncClient and rely on the SK configuration itself
            return OpenAIClientProvider.getClient();
        } else {
            // OPEN AI
            if (semanticKernelConfiguration.getClient().get().getOpenai().isPresent()) {
                // TBV Just quick and dirty hacks which need to be properly engineered
                Properties properties = new Properties();
                properties.put(OpenAISettings.getDefaultSettingsPrefix() + OpenAISettings.getKeySuffix(),
                        semanticKernelConfiguration.getClient().flatMap(client -> client.getOpenai().map(openai -> openai.getKey()))
                                .orElse(""));
                properties.put(OpenAISettings.getDefaultSettingsPrefix() + OpenAISettings.getOpenAiOrganizationSuffix(),
                        semanticKernelConfiguration.getClient()
                                .flatMap(client -> client.getOpenai().map(openai -> openai.getOrganizationid())).orElse(""));
                return new OpenAIClientProvider((Map) properties, ClientType.OPEN_AI).getAsyncClient();
            } else {
                // AZURE OPEN AI
                if (semanticKernelConfiguration.getClient().get().getAzureopenai().isPresent()) {
                    Properties properties = new Properties();
                    properties.put(AzureOpenAISettings.getDefaultSettingsPrefix() + AzureOpenAISettings.getKeySuffix(),
                            semanticKernelConfiguration.getClient()
                                    .flatMap(client -> client.getAzureopenai().map(azureopenai -> azureopenai.getKey())));
                    properties.put(
                            AzureOpenAISettings.getDefaultSettingsPrefix() + AzureOpenAISettings.getAzureOpenAiEndpointSuffix(),
                            semanticKernelConfiguration.getClient()
                                    .flatMap(client -> client.getAzureopenai().map(azureopenai -> azureopenai.getEndpoint())));
                    properties.put(
                            AzureOpenAISettings.getDefaultSettingsPrefix()
                                    + AzureOpenAISettings.getAzureOpenAiDeploymentNameSuffix(),
                            semanticKernelConfiguration.getClient()
                                    .flatMap(client -> client.getAzureopenai().map(azureopenai -> azureopenai.getDeploymentname())));
                    return new OpenAIClientProvider((Map) properties, ClientType.AZURE_OPEN_AI).getAsyncClient();
                } else {
                    throw new ConfigurationException(
                            ConfigurationException.ErrorCodes.NO_VALID_CONFIGURATIONS_FOUND,
                            "spring.semantic-kernel.client property found, but no openai or azureopenai sub-properties found");
                }
            }

        }
    }
}
