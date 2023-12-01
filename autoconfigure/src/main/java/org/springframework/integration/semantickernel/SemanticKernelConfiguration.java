package org.springframework.integration.semantickernel;

import java.util.Optional;

import org.springframework.integration.semantickernel.semanticfunctions.SemanticFunctionConfiguration;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "semantic-kernel")
public class SemanticKernelConfiguration {

    /**
     * OpenAI and AzureOpenAI Configuration Wrapper
     */
    private ClientConfig client;

    /**
     * Wrapper for Semantic Function Configuration
     */
    private SemanticFunctionConfiguration semanticFunction;

    @Data
    @NoArgsConstructor
    public static class ClientConfig {
        /**
         * Open AI Configuration
         */
        private OpenAIConfig openai;
        /**
         * Azure Open AI Configuration
         */
        private AzureOpenAIConfig azureopenai;
    }

    @Data
    @NoArgsConstructor
    public static class OpenAIConfig {
        /**
         * OpenAI API Key
         */
        private String key;
        /**
         * Open AI Organizational ID
         */
        private String organizationid;
    }

    @Data
    @NoArgsConstructor
    public static class AzureOpenAIConfig {
        /**
         * Azure Open AI Endpoint URL
         */
        private String endpoint;
        /**
         * Azure Open AI Key
         */
        private String key;
        /**
         * Azure Open AI Deployment name
         */
        private String deploymentname;
    }
}
