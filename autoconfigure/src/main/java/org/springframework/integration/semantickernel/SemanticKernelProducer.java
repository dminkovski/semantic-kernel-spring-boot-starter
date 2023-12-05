package org.springframework.integration.semantickernel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.SKBuilders;
import com.microsoft.semantickernel.chatcompletion.ChatCompletion;
import com.microsoft.semantickernel.chatcompletion.ChatHistory;
import org.springframework.stereotype.Component;

@Configuration
@Component
@ComponentScan(basePackages = "org.springframework.integration.semantickernel.semanticfunctions")
public class SemanticKernelProducer {

    @Autowired
    OpenAIAsyncClient client;

    @Bean
    public Kernel buildKernel() {
        ChatCompletion<ChatHistory> textCompletion = SKBuilders.chatCompletion()
                // .withStuffFromConfig
                .withOpenAIClient(client)
                .withModelId(SemanticKernelModel.TEXTEMBEDDINGADA002.getName())
                .build();

        return SKBuilders.kernel()
                // .withStuffFromConfig
                .withDefaultAIService(textCompletion)
                .build();
    }
}
