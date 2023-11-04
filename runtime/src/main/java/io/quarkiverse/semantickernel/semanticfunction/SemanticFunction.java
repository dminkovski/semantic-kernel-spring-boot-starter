package io.quarkiverse.semantickernel.semanticfunction;

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

        private String value;

        public static Literal of(String value) {
            return new Literal(value);
        }

        @Override
        public String value() {
            return value;
        }

        private Literal(String value) {
            this.value = value;
        }
    }

    @Nonbinding
    String value() default "";
}
