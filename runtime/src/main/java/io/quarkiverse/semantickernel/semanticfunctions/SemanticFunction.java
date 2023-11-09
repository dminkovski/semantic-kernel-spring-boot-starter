package io.quarkiverse.semantickernel.semanticfunctions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.enterprise.util.AnnotationLiteral;
import jakarta.enterprise.util.Nonbinding;
import jakarta.inject.Qualifier;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD })
public @interface SemanticFunction {
    public static final class Literal extends AnnotationLiteral<SemanticFunction> implements SemanticFunction {

        private String skill;
        private String function;

        public static Literal of(String skill, String function) {
            return new Literal(skill, function);
        }

        @Override
        public String skill() {
            return skill;
        }

        @Override
        public String function() {
            return function;
        }

        private Literal(String skill, String function) {
            this.skill = skill;
            this.function = function;
        }
    }

    @Nonbinding
    String skill() default "";

    @Nonbinding
    String function() default "";
}
