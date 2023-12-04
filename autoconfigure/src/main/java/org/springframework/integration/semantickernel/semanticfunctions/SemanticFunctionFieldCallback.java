package org.springframework.integration.semantickernel.semanticfunctions;

import com.microsoft.semantickernel.textcompletion.CompletionSKFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class SemanticFunctionFieldCallback implements ReflectionUtils.FieldCallback {
    private static Logger logger = LoggerFactory.getLogger(SemanticFunctionFieldCallback.class);
    private static String WARN_NON_COMPLETION_SK_FUNCTION = "@SemanticFunction(skill, function) "
            + "should be of type CompletionSKFunction.";
    private static String ERROR_CREATE_INSTANCE = "Cannot create instance of "
            + "type '{}' or instance creation is failed because: {}";

    private static int AUTOWIRE_MODE = AutowireCapableBeanFactory.AUTOWIRE_BY_NAME;
    private ConfigurableListableBeanFactory configurableBeanFactory;
    private SemanticFunctionProducer semanticFunctionProducer;
    private Object bean;

    public SemanticFunctionFieldCallback(ConfigurableListableBeanFactory bf, SemanticFunctionProducer sfp, Object bean) {
        configurableBeanFactory = bf;
        semanticFunctionProducer = sfp;
        this.bean = bean;
    }
    @Override
    public void doWith(Field field)
            throws IllegalArgumentException, IllegalAccessException {
        if (!field.isAnnotationPresent(SemanticFunction.class)) {
            return;
        }
        ReflectionUtils.makeAccessible(field);
        Type fieldGenericType = field.getGenericType();
        Class<?> generic = field.getType();
        String skill = field.getDeclaredAnnotation(SemanticFunction.class).skill();
        String function = field.getDeclaredAnnotation(SemanticFunction.class).function();

        if (typeIsValid(fieldGenericType)) {
            String beanName = function + skill + generic.getSimpleName();
            Object beanInstance = getBeanInstanceForCompletionSKFunction(beanName, field);
            field.set(bean, beanInstance);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public boolean typeIsValid(Type field) {
        if (field.getTypeName().equals("com.microsoft.semantickernel.textcompletion.CompletionSKFunction")) {
            return true;
        } else {
            logger.warn(WARN_NON_COMPLETION_SK_FUNCTION);
            return true;
        }
    }

    public Object getBeanInstanceForCompletionSKFunction(
            String beanName, Field field) {
        Object skFunctionInstance = null;
        if (!configurableBeanFactory.containsBean(beanName)) {
            logger.info("Creating new CompletionSKFunction bean named '{}'.", beanName);

            Object toRegister = null;
            try {
                toRegister = semanticFunctionProducer.buildFunction(field);
            } catch (Exception e) {
                logger.error(ERROR_CREATE_INSTANCE, "SemanticFunctionProducer", e);
                throw new RuntimeException(e);
            }

            skFunctionInstance = configurableBeanFactory.initializeBean(toRegister, beanName);
            configurableBeanFactory.autowireBeanProperties(skFunctionInstance, AUTOWIRE_MODE, true);
            configurableBeanFactory.registerSingleton(beanName, skFunctionInstance);
            logger.info("Bean named '{}' created successfully.", beanName);
        } else {
            skFunctionInstance = configurableBeanFactory.getBean(beanName);
            logger.info(
                    "Bean named '{}' already exists used as current bean reference.", beanName);
        }
        return skFunctionInstance;
    }
}
