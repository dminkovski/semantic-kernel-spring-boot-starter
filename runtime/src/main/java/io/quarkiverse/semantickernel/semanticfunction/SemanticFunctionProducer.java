package io.quarkiverse.semantickernel.semanticfunction;

import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.inject.Inject;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.SKBuilders;
import com.microsoft.semantickernel.exceptions.SkillsNotFoundException;
import com.microsoft.semantickernel.exceptions.SkillsNotFoundException.ErrorCodes;
import com.microsoft.semantickernel.semanticfunctions.PromptTemplateConfig;
import com.microsoft.semantickernel.semanticfunctions.PromptTemplateConfig.CompletionConfigBuilder;
import com.microsoft.semantickernel.semanticfunctions.SemanticFunctionConfig;
import com.microsoft.semantickernel.textcompletion.CompletionSKFunction;

import io.quarkiverse.semantickernel.SemanticKernelConfiguration;
import io.quarkus.arc.Unremovable;

@Unremovable
public class SemanticFunctionProducer {

    @Inject
    Kernel kernel;
    @Inject
    SemanticKernelConfiguration configuration;

    @Produces
    @SemanticFunction
    public CompletionSKFunction buildFunction(InjectionPoint ip) {

        var skillName = getSkillName(ip);

        var sfConfiguration = configuration.semanticFunction().get(skillName);
        if (sfConfiguration == null) {
            throw new SkillsNotFoundException(ErrorCodes.SKILLS_NOT_FOUND,
                    "Missing configuration for the semantic function: " + skillName);
        }

        var builder = configureBuilder(sfConfiguration);
        var tplConfig = new PromptTemplateConfig(builder.build());

        var promptTemplate = SKBuilders.promptTemplate()
                .withPromptTemplate(sfConfiguration.prompt())
                .withPromptTemplateConfig(tplConfig)
                .withPromptTemplateEngine(SKBuilders.promptTemplateEngine().build()).build();

        var semanticFConfig = new SemanticFunctionConfig(tplConfig, promptTemplate);

        return kernel.registerSemanticFunction(skillName, sfConfiguration.name(), semanticFConfig);
    }

    private CompletionConfigBuilder configureBuilder(SemanticFunctionConfiguration sfConfiguration) {
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

    private String getSkillName(InjectionPoint ip) {
        var rv = ip.getAnnotated().getAnnotation(SemanticFunction.class).value();
        return rv != null && !rv.isBlank() ? rv : "default";
    }
}
