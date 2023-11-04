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

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import com.azure.ai.openai.OpenAIAsyncClient;

@Path("/semantic-kernel")
@ApplicationScoped
public class SemanticKernelResource {

    @Inject
    OpenAIAsyncClient client;

    // @Inject
    // Kernel kernel;

    // @Inject
    // @SemanticFunction("test")
    // CompletionSKFunction test;

    // String input = null;
    // @PostConstruct
    // void loadUsher() throws IOException {
    //     try (var is = Thread.currentThread().getContextClassLoader().getResourceAsStream("usher.txt")) {
    //         input = new String(is.readAllBytes(), StandardCharsets.UTF_8);
    //     }
    // }

    @GET
    @Path("/hello")
    public String helloWithSemanticKernel() {
        // Creates an instance of the TextCompletion service
        //        TextCompletion textCompletion = SKBuilders.chatCompletion().withOpenAIClient(client).withModelId("gpt35turbo").build();
        //
        //        // Instantiates the Kernel
        //        Kernel kernel = SKBuilders.kernel().withDefaultAIService(textCompletion).build();
        //
        //        // Registers skills
        //        ReadOnlyFunctionCollection skill = kernel.importSkillFromResources("", "NarrationSkill", "NarrateFight");
        //        CompletionSKFunction fightFunction = skill.getFunction("NarrateFight", CompletionSKFunction.class);

        // var ctx = kernel.runAsync(input, test).block();
        // System.out.println("-----------");
        // System.out.println(ctx.getResult());
        // System.out.println("-----------");
        // /*
        //  * -----------
        //  * A narrator visits his sick friend Roderick Usher. Roderick believes the house is alive. His twin sister Madeline dies and returns from the grave. The house collapses and sinks into a lake.
        //  * -----------
        //  */

        return "Hello with Semantic Kernel";
    }

    @GET
    public String hello() {
        return "Hello semantic-kernel";
    }
}
