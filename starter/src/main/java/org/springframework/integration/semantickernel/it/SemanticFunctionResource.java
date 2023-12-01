package org.springframework.integration.semantickernel.it;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.textcompletion.CompletionSKFunction;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.integration.semantickernel.semanticfunctions.SemanticFunction;

@RequestMapping("/semantic-function")
@RestController
public class SemanticFunctionResource {

    // private static final Logger LOGGER = LoggerFactory.getLogger(SemanticFunctionResource.class);

    @Autowired
    Kernel kernel;

    @SemanticFunction(skill = "TestSkill", function = "TestFunction")
    CompletionSKFunction summarizeFunction;

    String textToSummarize = null;

    @PostConstruct
    void loadTextToSummarize() throws IOException {
        try (InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("textToSummarize.txt")) {
            textToSummarize = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    @GetMapping("/summarize")
    public String summarize() {

        // SKContext ctx = kernel.runAsync(textToSummarize, summarizeFunction).block();
        // LOGGER.info("Summary:");
        // LOGGER.info(ctx.getResult());

        return "Summarize completed";
    }

    @GetMapping("/joke")
    public String joke() {

        // ContextVariables variables = ContextVariables.builder()
        //         .withInput("A joke about software engineers")
        //         .withVariable("audience_type", "non technicians")
        //         .build();
        // SKContext ctx = kernel.runAsync(variables, jokeFunction).block();
        // LOGGER.info("Joke:");
        // LOGGER.info(ctx.getResult());

        return "Joke produced";
    }
}
