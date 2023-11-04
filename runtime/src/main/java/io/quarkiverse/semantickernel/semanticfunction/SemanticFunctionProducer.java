package io.quarkiverse.semantickernel.semanticfunction;

import java.util.Optional;
import java.util.function.Predicate;

import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.inject.Inject;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.SKBuilders;
import com.microsoft.semantickernel.exceptions.SkillsNotFoundException;
import com.microsoft.semantickernel.orchestration.SKFunction;
import com.microsoft.semantickernel.semanticfunctions.PromptTemplateConfig;
import com.microsoft.semantickernel.semanticfunctions.PromptTemplateConfig.CompletionConfigBuilder;
import com.microsoft.semantickernel.semanticfunctions.SemanticFunctionConfig;
import com.microsoft.semantickernel.skilldefinition.FunctionNotFound;
import com.microsoft.semantickernel.textcompletion.CompletionRequestSettings;
import com.microsoft.semantickernel.textcompletion.CompletionSKFunction;

import io.quarkiverse.semantickernel.SemanticKernelConfiguration;
import io.quarkiverse.semantickernel.semanticfunction.SemanticFunctionConfiguration.Skill.Function;

public class SemanticFunctionProducer {

    @Inject
    Kernel kernel;
    @Inject
    SemanticKernelConfiguration configuration;

    @Produces
    @SemanticFunction
    public CompletionSKFunction buildFunction(InjectionPoint ip) {
        var skillName = getSkillName(ip);
        var functionName = getFunctionName(ip);

        if (isKnownSemanticFunction(skillName, functionName)) {
            return kernel.getSkill(skillName).getFunction(functionName, CompletionSKFunction.class);
        } else {
            Optional<CompletionSKFunction> rv = loadFromDirectory(skillName, functionName);
            if (rv.isPresent()) {
                return rv.get();
            } else {
                return loadFromConfigurations(skillName, functionName);
            }
        }
    }

    private String getSkillName(InjectionPoint ip) {
        var rv = ip.getAnnotated().getAnnotation(SemanticFunction.class).skill();
        return rv != null && !rv.isBlank() ? rv : "default";
    }

    private String getFunctionName(InjectionPoint ip) {
        var rv = ip.getAnnotated().getAnnotation(SemanticFunction.class).function();
        return rv != null && !rv.isBlank() ? rv : "default";
    }

    private Optional<CompletionSKFunction> loadFromDirectory(String skillName, String functionName) {
        var directory = configuration.semanticFunction().fromDirectory();
        if (directory.isPresent()) {
            kernel.importSkillsFromDirectory(directory.get(), skillName);
            var knownFunction = isKnownSemanticFunction(skillName, functionName);
            return knownFunction
                    ? Optional.of(kernel.getSkill(skillName).getFunction(functionName, CompletionSKFunction.class))
                    : Optional.empty();
        }
        return Optional.empty();
    }

    private boolean isKnownSemanticFunction(String skillName, String functionName) {
        Predicate<SKFunction<?>> knownCompletionPredicate = f -> {
            return f.getName().equals(functionName)
                    && CompletionRequestSettings.class.equals(f.getType());
        };
        return kernel.getSkills().asMap().containsKey(skillName)
                && kernel.getSkill(skillName).getAll().stream().anyMatch(knownCompletionPredicate);
    }

    private CompletionSKFunction loadFromConfigurations(String skillName, String functionName) {
        var skillConfiguration = configuration.semanticFunction().skills().get(skillName);
        if (skillConfiguration == null) {
            throw new SkillsNotFoundException(
                    com.microsoft.semantickernel.exceptions.SkillsNotFoundException.ErrorCodes.SKILLS_NOT_FOUND,
                    "Missing configuration for theskill: " + skillName);
        }

        var functionConfiguration = skillConfiguration.functions().get(functionName);
        if (functionConfiguration == null) {
            throw new FunctionNotFound(
                    com.microsoft.semantickernel.skilldefinition.FunctionNotFound.ErrorCodes.FUNCTION_NOT_FOUND,
                    "Missing configuration for the semantic function: " + functionName + " in skill " + skillName);
        }

        var builder = configureBuilder(functionConfiguration);
        var tplConfig = new PromptTemplateConfig(builder.build());

        var promptTemplate = SKBuilders.promptTemplate()
                .withPromptTemplate(functionConfiguration.prompt())
                .withPromptTemplateConfig(tplConfig)
                .withPromptTemplateEngine(SKBuilders.promptTemplateEngine().build()).build();

        var semanticFConfig = new SemanticFunctionConfig(tplConfig, promptTemplate);

        return kernel.registerSemanticFunction(skillName, functionName, semanticFConfig);
    }

    private CompletionConfigBuilder configureBuilder(Function sfConfiguration) {
        var builder = new CompletionConfigBuilder();
        if (sfConfiguration.frequencyPenalty().isPresent()) {
            builder = builder.frequencyPenalty(sfConfiguration.frequencyPenalty().get());
        }
        if (sfConfiguration.maxTokens().isPresent()) {
            builder = builder.maxTokens(sfConfiguration.maxTokens().get());
        }
        if (sfConfiguration.presencePenalty().isPresent()) {
            builder = builder.presencePenalty(sfConfiguration.presencePenalty().get());
        }
        if (sfConfiguration.stopSequence().isPresent()) {
            builder = builder.stopSequences(sfConfiguration.stopSequence().get());
        }
        if (sfConfiguration.temperature().isPresent()) {
            builder = builder.temperature(sfConfiguration.temperature().get());
        }
        if (sfConfiguration.topP().isPresent()) {
            builder = builder.topP(sfConfiguration.topP().get());
        }
        return builder;
    }
}
