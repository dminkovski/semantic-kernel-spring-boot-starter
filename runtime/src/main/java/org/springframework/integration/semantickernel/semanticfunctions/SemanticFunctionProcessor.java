package org.springframework.integration.semantickernel.semanticfunctions;

import java.lang.reflect.Field;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class SemanticFunctionProcessor implements BeanPostProcessor {
    
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        for (Field field : beanClass.getFields()) {
            if (field.isAnnotationPresent(SemanticFunction.class)) {
                SemanticFunction annotation = field.getAnnotation(SemanticFunction.class);
                System.out.println("Found SemanticFunction: skill=" + annotation.skill() + ", function=" + annotation.function());
            }
        }
        return bean;
    }
}
