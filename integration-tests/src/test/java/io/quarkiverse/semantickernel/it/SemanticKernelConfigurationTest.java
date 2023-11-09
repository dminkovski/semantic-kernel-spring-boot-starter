package io.quarkiverse.semantickernel.it;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.quarkiverse.semantickernel.SemanticKernelConfiguration;
import io.quarkiverse.semantickernel.semanticfunctions.SemanticFunctionConfiguration.Skill;
import io.quarkiverse.semantickernel.semanticfunctions.SemanticFunctionConfiguration.Skill.Function;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class SemanticKernelConfigurationTest {

    @Inject
    SemanticKernelConfiguration configuration;

    @Nested
    public class Client {
        @Test
        public void testConfiguration() {
            assertEquals(Optional.of("OPEN_AI_KEY"),
                    configuration.client().flatMap(client -> client.openai().map(openai -> openai.key())));
            assertEquals(Optional.of("OPEN_AI_ORGANIZATION_ID"),
                    configuration.client().flatMap(client -> client.openai().map(openai -> openai.organizationid())));
            assertEquals(Optional.of("AZURE_OPEN_AI_KEY"),
                    configuration.client().flatMap(client -> client.azureopenai().map(azureopenai -> azureopenai.key())));
            assertEquals(Optional.of("AZURE_OPEN_AI_ENDPOINT"),
                    configuration.client().flatMap(client -> client.azureopenai().map(azureopenai -> azureopenai.endpoint())));
            assertEquals(Optional.of("AZURE_OPEN_AI_DEPLOYMENT_NAME"), configuration.client()
                    .flatMap(client -> client.azureopenai().map(azureopenai -> azureopenai.deploymentname())));
        }
    }

    @Nested
    public class SemanticFunction {
        @Test
        public void testConfiguration() {
            assertEquals(Optional.of("IMPORT_DIRECTORY"),
                    configuration.semanticFunction().flatMap(semanticFunction -> semanticFunction.fromDirectory()));

            String testFunctionExpectedPrompt = "{{$input}}\n\nSummarize the content above in less than 140 characters.";
            Optional<Skill> skill = configuration.semanticFunction().map(semanticFunction -> semanticFunction.skills())
                    .flatMap(skills -> Optional.ofNullable(skills.get("TestSkill")));
            assertEquals(Optional.of(testFunctionExpectedPrompt),
                    skill.map(Skill::functions).flatMap(functions -> Optional.ofNullable(functions.get("TestFunction")))
                            .map(Function::prompt));

        }
    }
}
