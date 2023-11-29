package org.springframework.integration.semantickernel.semanticfunctions;

import java.lang.reflect.Field;

import com.microsoft.semantickernel.orchestration.SKFunction;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class SemanticFunctionProcessor implements BeanPostProcessor {

    @Autowired
    private ConfigurableListableBeanFactory configurableBeanFactory;

    @Autowired
    private SemanticFunctionProducer functionProducer;
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return fillBeanFieldValue(bean);
    }

    private Object fillBeanFieldValue(Object bean){
        ReflectionUtils.doWithFields(bean.getClass(),
                new ReflectionUtils.FieldCallback() {
                    @Override
                    public void doWith(final Field field) throws IllegalArgumentException, IllegalAccessException {
                        SKFunction builtFunction = functionProducer.buildFunction(field);
                        field.set(bean, builtFunction);
                        System.out.println("Filled "+field.getName());
                    }
                },
                new ReflectionUtils.FieldFilter() {
                    @Override
                    public boolean matches(final Field field) {
                        return field.getAnnotation(SemanticFunction.class) != null;
                    }
                });
        return bean;
    }

    /**
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(SemanticFunction.class)) {
                try{
                    field.set(bean, functionProducer.buildFunction(field));
                }catch(Exception e){
                    e.printStackTrace();
                }
                return bean;
            }
        }
        return bean;
    }*/
}
