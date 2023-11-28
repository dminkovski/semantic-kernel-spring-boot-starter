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
@ConfigurationProperties(prefix = "spring.semantic-kernel")
public class SemanticKernelConfiguration {
    
    private ClientConfig client;
    private SemanticFunctionConfiguration semanticFunction;

    @Data
    @NoArgsConstructor
    public static class ClientConfig {
        private OpenAIConfig openai;
        private AzureOpenAIConfig azureopenai;
    }

    @Data
    @NoArgsConstructor
    public static class OpenAIConfig {
        private String key;
        private String organizationid;
    }

    @Data
    @NoArgsConstructor
    public static class AzureOpenAIConfig {
        private String endpoint;
        private String key;
        private String deploymentname;
    }
}
