package com.otavio.jakarta.cdi;

import jakarta.enterprise.context.ApplicationScoped;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Specifies that a CDI bean is enabled only when a runtime setting resolves to
 * the expected value.
 * <p>
 * A setting represents a named value available to the CDI container during bean
 * discovery or bootstrap. The source of the setting is container-defined and may
 * include system properties, environment variables, configuration files,
 * deployment descriptors, or other runtime configuration mechanisms.
 * </p>
 * <p>
 * When this annotation is declared on a bean class, producer method, or producer
 * field, the CDI container must evaluate the setting before the bean participates
 * in typesafe resolution.
 * </p>
 * <p>
 * If the setting identified by {@link #name()} does not resolve to the value
 * declared by {@link #value()}, the annotated bean is not enabled and must not
 * participate in typesafe resolution.
 * </p>
 * <p>
 * This annotation supports a single setting check. If a bean requires multiple
 * checks or more complex activation logic, use {@link RequiresCondition}
 * instead.
 * </p>
 *
 * <pre>{@code
 * @RequiresSetting(name = "app.country", value = "BR")
 * @ApplicationScoped
 * public class BrazilTaxCalculator implements TaxCalculator {
 * }
 * }</pre>
 *
 * @see RequiresCondition
 * @see Condition
 */
@Documented
@Retention(RUNTIME)
@Target({TYPE, METHOD, FIELD})
@ApplicationScoped
public @interface RequiresSetting {

    /**
     * The name of the runtime setting to resolve.
     *
     * @return the setting name
     */
    String name();

    /**
     * The expected value of the runtime setting.
     *
     * @return the required setting value
     */
    String value();
}
