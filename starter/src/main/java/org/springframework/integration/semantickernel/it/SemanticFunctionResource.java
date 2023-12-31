package org.springframework.integration.semantickernel.it;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.KernelConfig;
import com.microsoft.semantickernel.orchestration.ContextVariables;
import com.microsoft.semantickernel.orchestration.SKContext;
import com.microsoft.semantickernel.textcompletion.CompletionSKFunction;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.integration.semantickernel.semanticfunctions.SemanticFunction;

@RequestMapping("/semantic-function")
@RestController
@ComponentScan("org.springframework.integration.semantickernel")
public class SemanticFunctionResource {


    private static final Logger LOGGER = LoggerFactory.getLogger(SemanticFunctionResource.class);

    @Autowired
    Kernel kernel;

    @SemanticFunction(skill = "TestSkill", function = "TestFunction")
    CompletionSKFunction summarizeFunction;
    String textToSummarize = null;

    @PostConstruct
    void loadTextToSummarize() throws IOException {
        try (InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("textToSummarize.txt")) {
            summarizeFunction.invokeAsync();
            textToSummarize = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    @GetMapping("/summarize")
    public String summarize() {
         SKContext ctx = kernel.runAsync(textToSummarize, summarizeFunction).block(Duration.ofSeconds(30));
         LOGGER.info("Summary:");
         LOGGER.info(ctx.getResult());

        return "Summarize completed";
    }

    @GetMapping("/joke")
    public String joke() {
        ContextVariables variables = ContextVariables.builder()
                 .withInput("A joke about software engineers")
                 .withVariable("audience_type", "non technicians")
                 .build();
         SKContext ctx = kernel.runAsync(variables, summarizeFunction).block();
         LOGGER.info("Joke:");
         LOGGER.info(ctx.getResult());

        return "Joke produced";
    }
}
