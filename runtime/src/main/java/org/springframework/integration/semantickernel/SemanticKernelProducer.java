package org.springframework.integration.semantickernel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.SKBuilders;
import com.microsoft.semantickernel.chatcompletion.ChatCompletion;
import com.microsoft.semantickernel.chatcompletion.ChatHistory;

@Configuration
public class SemanticKernelProducer {
    public static String modelId = "gpt-3.5-turbo";

    @Autowired
    OpenAIAsyncClient client;

    @Bean
    public Kernel buildKernel() {
        ChatCompletion<ChatHistory> textCompletion = SKBuilders.chatCompletion()
                // .withStuffFromConfig
                .withOpenAIClient(client)
                .withModelId(SemanticKernelProducer.modelId)
                .build();

        return SKBuilders.kernel()
                // .withStuffFromConfig
                .withDefaultAIService(textCompletion)
                .build();
    }
}