package com.otavio.jakarta.cdi.extension;

import com.otavio.jakarta.cdi.Condition;
import com.otavio.jakarta.cdi.RequiresCondition;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.Annotated;
import jakarta.enterprise.inject.spi.BeanAttributes;
import jakarta.enterprise.inject.spi.DefinitionException;
import jakarta.enterprise.inject.spi.Extension;
import jakarta.enterprise.inject.spi.ProcessBeanAttributes;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Portable extension that disables CDI beans annotated with
 * {@link RequiresCondition} when the declared {@link Condition} evaluates to
 * {@code false}.
 * <p>
 * This extension observes {@link ProcessBeanAttributes} so the condition can be
 * applied to bean classes, producer methods, and producer fields.
 * </p>
 */
public class RequiresConditionExtension implements Extension {

    private static final Logger LOGGER = Logger.getLogger(RequiresConditionExtension.class.getName());
    /**
     * Evaluates {@link RequiresCondition} before the bean participates in CDI
     * typesafe resolution.
     *
     * @param event the process bean attributes event
     * @param <T> the bean type
     */
    <T> void processBeanAttributes(@Observes ProcessBeanAttributes<T> event) {
        Annotated annotated = event.getAnnotated();
        RequiresCondition requiresCondition = annotated.getAnnotation(RequiresCondition.class);

        if (requiresCondition == null) {
            return;
        }

        Class<? extends Condition> conditionClass = requiresCondition.value();
        BeanAttributes<T> beanAttributes = event.getBeanAttributes();

        LOGGER.log(Level.FINE,
                "Evaluating condition {0} for CDI bean with types {1} and qualifiers {2}",
                new Object[]{
                        conditionClass.getName(),
                        beanAttributes.getTypes(),
                        beanAttributes.getQualifiers()
                });

        Condition condition = createCondition(conditionClass);
        boolean matches = evaluateCondition(condition, conditionClass, beanAttributes);

        if (!matches) {
            LOGGER.log(Level.INFO,
                    "Vetoing CDI bean with types {0} because condition {1} evaluated to false",
                    new Object[]{
                            beanAttributes.getTypes(),
                            conditionClass.getName()
                    });
            event.veto();
            return;
        }

        LOGGER.log(Level.FINE,
                "CDI bean with types {0} remains enabled because condition {1} evaluated to true",
                new Object[]{
                        beanAttributes.getTypes(),
                        conditionClass.getName()
                });
    }

    private Condition createCondition(Class<? extends Condition> conditionClass) {
        try {
            return conditionClass.getConstructor().newInstance();
        } catch (NoSuchMethodException exception) {
            String message = "Condition class must declare a public no-argument constructor: "
                    + conditionClass.getName();

            LOGGER.log(Level.SEVERE, message, exception);
            throw new DefinitionException(message, exception);
        } catch (InstantiationException | IllegalAccessException exception) {
            String message = "Condition class could not be instantiated: "
                    + conditionClass.getName();

            LOGGER.log(Level.SEVERE, message, exception);
            throw new DefinitionException(message, exception);
        } catch (InvocationTargetException exception) {
            Throwable cause = exception.getCause();

            String message = "Condition constructor failed: "
                    + conditionClass.getName();

            LOGGER.log(Level.SEVERE, message, cause);
            throw new DefinitionException(message, cause);
        }
    }

    private boolean evaluateCondition(Condition condition,
                                      Class<? extends Condition> conditionClass,
                                      BeanAttributes<?> beanAttributes) {
        try {
            return condition.test();
        } catch (RuntimeException exception) {
            String message = "Condition evaluation failed for "
                    + conditionClass.getName()
                    + " while evaluating CDI bean with types "
                    + beanAttributes.getTypes();

            LOGGER.log(Level.SEVERE, message, exception);
            throw new DefinitionException(message, exception);
        }
    }
}