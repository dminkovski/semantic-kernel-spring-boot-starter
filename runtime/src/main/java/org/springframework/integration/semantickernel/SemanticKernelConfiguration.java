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
@Component
@ConfigurationProperties(prefix = "spring.semantic-kernel")
public class SemanticKernelConfiguration {
    
    private Optional<ClientConfig> client = Optional.empty();
    private Optional<SemanticFunctionConfiguration> semanticFunction = Optional.empty();

    @Data
    @NoArgsConstructor
    public static class ClientConfig {
        private Optional<OpenAIConfig> openai = Optional.empty();
        private Optional<AzureOpenAIConfig> azureopenai = Optional.empty();
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
