/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package io.quarkiverse.semantickernel.it;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.textcompletion.CompletionSKFunction;

import io.quarkiverse.semantickernel.semanticfunctions.SemanticFunction;

@Path("/semantic-function")
@ApplicationScoped
public class SemanticFunctionResource {

    // private static final Logger LOGGER = LoggerFactory.getLogger(SemanticFunctionResource.class);

    @Inject
    Kernel kernel;

    @Inject
    // This semantic function comes from application properties
    @SemanticFunction(skill = "TestSkill", function = "TestFunction")
    CompletionSKFunction summarizeFunction;

    // @Inject
    // This semantic function has been loaded from a directory
    // -Dquarkus.semantic-kernel.semantic-function.from-directory=<project_root>/quarkus-semantic-kernel/integration-tests/src/test/resources/skills
    // @SemanticFunction(skill = "FunSkill", function = "Joke")
    // CompletionSKFunction jokeFunction;

    String textToSummarize = null;

    @PostConstruct
    void loadTextToSummarize() throws IOException {
        try (InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("textToSummarize.txt")) {
            textToSummarize = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    @GET
    @Path("/summarize")
    public String summarize() {

        // SKContext ctx = kernel.runAsync(textToSummarize, summarizeFunction).block();
        // LOGGER.info("Summary:");
        // LOGGER.info(ctx.getResult());

        return "Summarize completed";
    }

    @GET
    @Path("/joke")
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
