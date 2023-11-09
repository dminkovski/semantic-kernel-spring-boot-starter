package io.quarkiverse.semantickernel;

import java.util.Map;
import java.util.Properties;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.microsoft.semantickernel.connectors.ai.openai.util.AzureOpenAISettings;
import com.microsoft.semantickernel.connectors.ai.openai.util.ClientType;
import com.microsoft.semantickernel.connectors.ai.openai.util.OpenAIClientProvider;
import com.microsoft.semantickernel.connectors.ai.openai.util.OpenAISettings;
import com.microsoft.semantickernel.exceptions.ConfigurationException;

public class SemanticKernelClientProducer {

    private static final String AZURE_OPEN_AI_ENDPOINT_SUFFIX = "endpoint";
    private static final String AZURE_OPEN_AI_DEPLOYMENT_NAME_SUFFIX = "deploymentname";

    @Inject
    SemanticKernelConfiguration semanticKernelConfiguration;

    @Produces
    @ApplicationScoped
    public OpenAIAsyncClient produceOpenAIAsyncClient() throws ConfigurationException {

        if (semanticKernelConfiguration.client().isEmpty()) {
            // There is no Semantic Kernel configuration at the Quarkus level (not in the application.properties file, etc.)
            // We should return a default OpenAIAsyncClient and rely on the SK configuration itself
            return OpenAIClientProvider.getClient();
        } else {
            // OPEN AI
            if (semanticKernelConfiguration.client().get().openai().isPresent()) {
                // TBV Just quick and dirty hacks which need to be properly engineered
                Properties properties = new Properties();
                properties.put("client.openai." + OpenAISettings.KEY_SUFFIX,
                        semanticKernelConfiguration.client().flatMap(client -> client.openai().map(openai -> openai.key()))
                                .orElse(""));
                properties.put("client.openai." + OpenAISettings.OPEN_AI_ORGANIZATION_SUFFIX,
                        semanticKernelConfiguration.client()
                                .flatMap(client -> client.openai().map(openai -> openai.organizationid())).orElse(""));
                return new OpenAIClientProvider((Map) properties, ClientType.OPEN_AI).getAsyncClient();
            } else {
                // AZURE OPEN AI
                if (semanticKernelConfiguration.client().get().azureopenai().isPresent()) {
                    Properties properties = new Properties();
                    properties.put(AzureOpenAISettings.KEY_SUFFIX, semanticKernelConfiguration.client()
                            .flatMap(client -> client.azureopenai().map(azureopenai -> azureopenai.key())));
                    properties.put(AZURE_OPEN_AI_ENDPOINT_SUFFIX, semanticKernelConfiguration.client()
                            .flatMap(client -> client.azureopenai().map(azureopenai -> azureopenai.endpoint())));
                    properties.put(AZURE_OPEN_AI_DEPLOYMENT_NAME_SUFFIX, semanticKernelConfiguration.client()
                            .flatMap(client -> client.azureopenai().map(azureopenai -> azureopenai.deploymentname())));
                    return new OpenAIClientProvider((Map) properties, ClientType.AZURE_OPEN_AI).getAsyncClient();
                } else {
                    throw new ConfigurationException(
                            ConfigurationException.ErrorCodes.NO_VALID_CONFIGURATIONS_FOUND,
                            "quarkus.semantic-kernel.client property found, but no openai or azureopenai sub-properties found");
                }
            }

        }
    }
}
