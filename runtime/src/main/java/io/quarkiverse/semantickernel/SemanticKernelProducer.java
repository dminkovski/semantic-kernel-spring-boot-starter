package io.quarkiverse.semantickernel;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.SKBuilders;

public class SemanticKernelProducer {
    // Just a >placeholder<, before the actual implementation, to enable the
    // semantic function registration and test

    @Inject
    OpenAIAsyncClient client;

    @Produces
    public Kernel buildKernel() {
        var textCompletion = SKBuilders.chatCompletion()
                // .withStuffFromConfig
                .withOpenAIClient(client)
                .withModelId("gpt-3.5-turbo")
                .build();

        return SKBuilders.kernel()
                // .withStuffFromConfig
                .withDefaultAIService(textCompletion)
                .build();
    }
}
