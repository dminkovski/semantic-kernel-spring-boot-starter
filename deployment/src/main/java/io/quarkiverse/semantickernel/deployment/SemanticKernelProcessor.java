package io.quarkiverse.semantickernel.deployment;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

class SemanticKernelProcessor {

    private static final String FEATURE = "semantic-kernel";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }
}
