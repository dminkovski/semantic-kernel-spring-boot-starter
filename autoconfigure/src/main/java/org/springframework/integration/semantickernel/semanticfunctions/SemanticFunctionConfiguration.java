package org.springframework.integration.semantickernel.semanticfunctions;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "semantic-function")
@Configuration
public class SemanticFunctionConfiguration {

    /**
     * Runtime definition of skills
     */
    private Map<String, Skill> skills;

    /**
     * Lookup directory for packaged skills
     */
    private String fromDirectory;

    public Optional<String> fromDirectory(){
        return Optional.ofNullable(fromDirectory);
    }

    @Data
    @NoArgsConstructor
    public static class Skill {
        private Map<String, Function> functions;
       /**
         * Interface representing the configuration for a semantic function.
         * 
         *  prompt - Semantic function prompt.
         *  frequencyPenalty - Number between -2.0 and 2.0. Positive values penalize new tokens based on
         *        their existing frequency in the text so far, decreasing the model's
         *        likelihood to repeat the same line verbatim.
         *  maxTokens - Maximum number of tokens to generate. The total length of input tokens
         *        and generated tokens is limited by the model's context length.
         *  stopSequence - Up to 4 sequences where the API will stop generating further tokens.
         *  temperature - What sampling temperature to use, between 0 and 2. Higher values like 0.8
         *        will make the output more random, while lower values like 0.2 will make it
         *        more focused and deterministic. Generally, it's recommended to alter this or top_p but not both.
         *  topP - An alternative to sampling with temperature, called nucleus sampling, where
         *        the model considers the results of the tokens with top_p probability mass. So
         *        0.1 means only the tokens comprising the top 10% probability mass are
         *        considered. Generally, it's recommended to alter this or temperature but not both.
         */
       @Data
       @NoArgsConstructor
        public static class Function {
            String prompt;
            Double frequencyPenalty;
            Integer maxTokens;
            Double presencePenalty;
            List<String> stopSequences;
            Double temperature;
            Double topP;

            Integer bestOf;

            String user;

            public Optional<String> prompt(){
                return Optional.ofNullable(prompt);
            }
            public Optional<Double> frequencyPenalty(){
                return Optional.ofNullable(frequencyPenalty);
            }
            public Optional<Integer> maxTokens(){
                return Optional.ofNullable(maxTokens);
            }
            public Optional<Double> presencePenalty(){
                return Optional.ofNullable(presencePenalty);
            }
            public Optional<List<String>> stopSequences(){
                return Optional.ofNullable(stopSequences);
            }
            public Optional<Double> temperature(){
                return Optional.ofNullable(temperature);
            }
            public Optional<Double> topP(){
                return Optional.ofNullable(topP);
            }


        }
    }
}
