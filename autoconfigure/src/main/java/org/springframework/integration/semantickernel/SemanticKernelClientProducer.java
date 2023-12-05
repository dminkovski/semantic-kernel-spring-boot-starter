package org.springframework.integration.semantickernel;

import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationPropertiesScan("org.springframework.integration.semantickernel.SemanticKernelConfiguration")
public class SemanticKernelClientProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SemanticKernelClientProducer.class);
    @Autowired
    SemanticKernelConfiguration semanticKernelConfiguration;

    @Scope("singleton")
    @Bean
    public OpenAIAsyncClient produceOpenAIAsyncClient() throws ConfigurationException {
        if (semanticKernelConfiguration.getClient() == null) {
            LOGGER.info("No Semantic Kernel Configuration available in application.properties - using default OpenAIAsyncClient");
            // There is no Semantic Kernel configuration at the Spring level (not in the application.properties file, etc.)
            // We should return a default OpenAIAsyncClient and rely on the SK configuration itself
            return OpenAIClientProvider.getClient();
        } else {
            // OPEN AI
            if (semanticKernelConfiguration.getClient().getOpenai() != null) {
                // TBV Just quick and dirty hacks which need to be properly engineered
                Properties properties = new Properties();
                properties.put(OpenAISettings.getDefaultSettingsPrefix() + "." + OpenAISettings.getKeySuffix(),
                        semanticKernelConfiguration.getClient().getOpenai().getKey());
                properties.put(OpenAISettings.getDefaultSettingsPrefix() + "." +OpenAISettings.getOpenAiOrganizationSuffix(),
                        semanticKernelConfiguration.getClient().getOpenai().getOrganizationid());
                        System.out.println(semanticKernelConfiguration.toString());
                LOGGER.info(properties.toString());
                return new OpenAIClientProvider((Map) properties, ClientType.OPEN_AI).getAsyncClient();
            } else {
                // AZURE OPEN AI
                if (semanticKernelConfiguration.getClient().getAzureopenai() != null) {
                    Properties properties = new Properties();
                    properties.put(AzureOpenAISettings.getDefaultSettingsPrefix() + "." + AzureOpenAISettings.getKeySuffix(),
                            semanticKernelConfiguration.getClient().getAzureopenai().getKey());
                    properties.put(
                            AzureOpenAISettings.getDefaultSettingsPrefix() + "." + AzureOpenAISettings.getAzureOpenAiEndpointSuffix(),
                            semanticKernelConfiguration.getClient().getAzureopenai().getEndpoint());
                    properties.put(
                            AzureOpenAISettings.getDefaultSettingsPrefix() + "."
                                    + AzureOpenAISettings.getAzureOpenAiDeploymentNameSuffix(),
                            semanticKernelConfiguration.getClient().getAzureopenai().getDeploymentname());
                    LOGGER.info(properties.toString());
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
