package org.springframework.integration.semantickernel.semanticfunctions;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.semantic-function")
public interface SemanticFunctionConfiguration {

    /**
     * Runtime definition of skills
     */
    Map<String, Skill> skills();

    /**
     * Lookup directory for packaged skills
     */
    Optional<String> fromDirectory();

    interface Skill {
        Map<String, Function> functions();
       /**
         * Interface representing the configuration for a semantic function.
         * 
         * @param prompt Semantic function prompt.
         * @param frequencyPenalty Number between -2.0 and 2.0. Positive values penalize new tokens based on
         *        their existing frequency in the text so far, decreasing the model's
         *        likelihood to repeat the same line verbatim.
         * @param maxTokens Maximum number of tokens to generate. The total length of input tokens
         *        and generated tokens is limited by the model's context length.
         * @param stopSequence Up to 4 sequences where the API will stop generating further tokens.
         * @param temperature What sampling temperature to use, between 0 and 2. Higher values like 0.8
         *        will make the output more random, while lower values like 0.2 will make it
         *        more focused and deterministic. Generally, it's recommended to alter this or top_p but not both.
         * @param topP An alternative to sampling with temperature, called nucleus sampling, where
         *        the model considers the results of the tokens with top_p probability mass. So
         *        0.1 means only the tokens comprising the top 10% probability mass are
         *        considered. Generally, it's recommended to alter this or temperature but not both.
         */
        interface Function {
            String prompt();
            Optional<Double> frequencyPenalty();
            Optional<Integer> maxTokens();
            Optional<Double> presencePenalty();
            Optional<List<String>> stopSequence();
            Optional<Double> temperature();
            Optional<Double> topP();
        }
    }
}
