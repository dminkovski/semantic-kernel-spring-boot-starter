package io.quarkiverse.semantickernel.deployment;

import io.quarkiverse.semantickernel.SemanticKernelClientProducer;
import io.quarkiverse.semantickernel.SemanticKernelProducer;
import io.quarkiverse.semantickernel.semanticfunctions.SemanticFunctionProducer;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

class SemanticKernelProcessor {

    private static final String FEATURE = "semantic-kernel";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    AdditionalBeanBuildItem createProviders() {
        return AdditionalBeanBuildItem.builder()
                .addBeanClasses(
                        SemanticKernelProducer.class,
                        SemanticFunctionProducer.class,
                        SemanticKernelClientProducer.class)
                .setUnremovable()
                .build();
    }
}
