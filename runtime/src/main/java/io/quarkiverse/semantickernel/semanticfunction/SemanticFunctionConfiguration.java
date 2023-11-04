package io.quarkiverse.semantickernel.semanticfunction;

import java.util.List;
import java.util.Optional;

import io.quarkus.runtime.annotations.ConfigGroup;

@ConfigGroup
public interface SemanticFunctionConfiguration {

    /**
     * Name of the semantic function to register.
     */
    String name();

    /**
     * Prompt of the semantic function.
     */
    String prompt();

    /**
     * Number between -2.0 and 2.0. Positive values penalize new tokens based on
     * their existing frequency in the text so far, decreasing the model's
     * likelihood to repeat the same line verbatim.
     */
    Optional<Double> frequencyPenalty();

    /**
     * The maximum number of tokens to generate. The total length of input tokens
     * and generated tokens is limited by the model's context length.
     */
    Optional<Integer> maxTokens();

    /**
     * Number between -2.0 and 2.0. Positive values penalize new tokens based on
     * whether they appear in the text so far, increasing the model's likelihood to
     * talk about new topics.
     */
    Optional<Double> presencePenalty();

    /**
     * Up to 4 sequences where the API will stop generating further tokens.
     */
    Optional<List<String>> stopSequence();

    /**
     * What sampling temperature to use, between 0 and 2. Higher values like 0.8
     * will make the output more random, while lower values like 0.2 will make it
     * more focused and deterministic. We generally recommend altering this or top_p
     * but not both.
     */
    Optional<Double> temperature();

    /**
     * An alternative to sampling with temperature, called nucleus sampling, where
     * the model considers the results of the tokens with top_p probability mass. So
     * 0.1 means only the tokens comprising the top 10% probability mass are
     * considered. We generally recommend altering this or temperature but not both.
     */
    Optional<Double> topP();
}
