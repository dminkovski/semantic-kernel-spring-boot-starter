package io.quarkiverse.semantickernel;

import java.util.Optional;

import io.quarkiverse.semantickernel.semanticfunctions.SemanticFunctionConfiguration;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "quarkus.semantic-kernel")
@ConfigRoot(phase = ConfigPhase.RUN_TIME)
public interface SemanticKernelConfiguration {

    /**
     * Configuration properties for the client Semantic Kernel can connect to (openai, azureopenai).
     */
    Optional<ClientConfig> client();

    /**
     * Configuration of semantic functions.
     */
    Optional<SemanticFunctionConfiguration> semanticFunction();

    interface ClientConfig {
        /**
         * Configuration properties for the OpenAI client
         */
        Optional<OpenAIConfig> openai();

        /**
         * Configuration properties for the Azure OpenAI client
         */
        Optional<AzureOpenAIConfig> azureopenai();
    }

    interface OpenAIConfig {
        /**
         * OpenAI API key
         */
        String key();

        /**
         * OpenAI organization ID
         */
        String organizationid();
    }

    interface AzureOpenAIConfig {
        /**
         * Azure OpenAI endpoint URL
         */
        String endpoint();

        /**
         * Azure OpenAI endpoint key
         */
        String key();

        /**
         * Azure OpenAI deployment name
         */
        String deploymentname();
    }
}
