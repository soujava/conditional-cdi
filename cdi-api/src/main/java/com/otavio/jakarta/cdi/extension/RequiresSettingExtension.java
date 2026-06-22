package com.otavio.jakarta.cdi.extension;

import com.otavio.jakarta.cdi.RequiresSetting;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.Annotated;
import jakarta.enterprise.inject.spi.BeanAttributes;
import jakarta.enterprise.inject.spi.DefinitionException;
import jakarta.enterprise.inject.spi.Extension;
import jakarta.enterprise.inject.spi.ProcessBeanAttributes;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Portable extension that disables CDI beans annotated with
 * {@link RequiresSetting} when the declared runtime setting does not resolve to
 * the expected value.
 * <p>
 * This implementation resolves settings through Eclipse MicroProfile Config.
 * The setting may come from any configuration source supported by the active
 * MicroProfile Config implementation, such as system properties, environment
 * variables, configuration files, or custom configuration sources.
 * </p>
 * <p>
 * This extension is loaded through the Java {@link java.util.ServiceLoader}
 * mechanism. Implementations must provide the following service provider
 * configuration file:
 * </p>
 *
 * <pre>{@code
 * META-INF/services/jakarta.enterprise.inject.spi.Extension
 * }</pre>
 *
 * <p>
 * The file must contain the fully qualified name of this extension class:
 * </p>
 *
 * <pre>{@code
 * jakarta.enterprise.inject.condition.RequiresSettingExtension
 * }</pre>
 * <p>
 * This extension observes {@link ProcessBeanAttributes} so the setting
 * requirement can be applied to bean classes, producer methods, and producer
 * fields.
 * </p>
 */
public class RequiresSettingExtension implements Extension {

    private static final Logger LOGGER = Logger.getLogger(RequiresSettingExtension.class.getName());

    /**
     * Evaluates {@link RequiresSetting} before the bean participates in CDI
     * typesafe resolution.
     *
     * @param event the process bean attributes event
     * @param <T> the bean type
     */
    <T> void processBeanAttributes(@Observes ProcessBeanAttributes<T> event) {
        Annotated annotated = event.getAnnotated();
        RequiresSetting requiresSetting = annotated.getAnnotation(RequiresSetting.class);

        if (requiresSetting == null) {
            return;
        }

        BeanAttributes<T> beanAttributes = event.getBeanAttributes();

        String name = requiresSetting.name();
        String expectedValue = requiresSetting.value();

        LOGGER.log(Level.FINE,
                "Evaluating setting requirement {0}={1} for CDI bean with types {2} and qualifiers {3}",
                new Object[]{
                        name,
                        expectedValue,
                        beanAttributes.getTypes(),
                        beanAttributes.getQualifiers()
                });

        boolean matches = matches(name, expectedValue, beanAttributes);

        if (!matches) {
            LOGGER.log(Level.INFO,
                    "Vetoing CDI bean with types {0} because setting {1} does not resolve to expected value {2}",
                    new Object[]{
                            beanAttributes.getTypes(),
                            name,
                            expectedValue
                    });
            event.veto();
            return;
        }

        LOGGER.log(Level.FINE,
                "CDI bean with types {0} remains enabled because setting {1} resolved to expected value {2}",
                new Object[]{
                        beanAttributes.getTypes(),
                        name,
                        expectedValue
                });
    }

    private boolean matches(String name, String expectedValue, BeanAttributes<?> beanAttributes) {
        try {
            Config config = ConfigProvider.getConfig();
            Optional<String> value = config.getOptionalValue(name, String.class);

            if (value.isEmpty()) {
                LOGGER.log(Level.FINE,
                        "Setting {0} was not found while evaluating CDI bean with types {1}",
                        new Object[]{
                                name,
                                beanAttributes.getTypes()
                        });
                return false;
            }

            String actualValue = value.get();

            LOGGER.log(Level.FINE,
                    "Setting {0} resolved to {1} while expecting {2} for CDI bean with types {3}",
                    new Object[]{
                            name,
                            actualValue,
                            expectedValue,
                            beanAttributes.getTypes()
                    });

            return expectedValue.equals(actualValue);
        } catch (RuntimeException exception) {
            String message = "Setting evaluation failed for "
                    + name
                    + " while evaluating CDI bean with types "
                    + beanAttributes.getTypes();

            LOGGER.log(Level.SEVERE, message, exception);
            throw new DefinitionException(message, exception);
        }
    }
}