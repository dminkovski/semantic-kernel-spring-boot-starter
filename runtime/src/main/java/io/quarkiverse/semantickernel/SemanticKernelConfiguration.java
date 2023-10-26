package io.quarkiverse.semantickernel;

import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;

import java.util.Optional;

@ConfigMapping(prefix = "quarkus.semantickernel")
@ConfigRoot(phase = ConfigPhase.RUN_TIME)
public interface SemanticKernelConfiguration {

    /**
     * Configuration properties for the client Semantic Kernel can connect to (openai, azureopenai).
     */
    Optional<ClientConfig> client();

    interface ClientConfig {
        /**
         * Configuration properties for the OpenAI client
         */
        OpenAIConfig openai();

        /**
         * Configuration properties for the Azure OpenAI client
         */
        AzureOpenAIConfig azureopenai();
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
