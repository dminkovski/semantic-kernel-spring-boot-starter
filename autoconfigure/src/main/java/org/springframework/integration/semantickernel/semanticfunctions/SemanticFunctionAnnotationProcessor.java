package org.springframework.integration.semantickernel.semanticfunctions;

import java.lang.reflect.Field;

import com.microsoft.semantickernel.textcompletion.CompletionSKFunction;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class SemanticFunctionAnnotationProcessor implements BeanPostProcessor {

    @Autowired
    private ConfigurableListableBeanFactory configurableBeanFactory;

    @Autowired
    private SemanticFunctionProducer functionProducer;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        this.scanDataAccessAnnotation(bean, beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }
    protected void scanDataAccessAnnotation(Object bean, String beanName) {
        this.configureFieldInjection(bean);
    }

    private void configureFieldInjection(Object bean) {
        Class<?> managedBeanClass = bean.getClass();
        ReflectionUtils.FieldCallback fieldCallback =
                new SemanticFunctionFieldCallback(this.configurableBeanFactory, this.functionProducer, bean);
        ReflectionUtils.doWithFields(managedBeanClass, fieldCallback);
    }
}
