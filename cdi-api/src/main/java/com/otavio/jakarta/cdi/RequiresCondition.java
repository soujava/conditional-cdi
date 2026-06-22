package com.otavio.jakarta.cdi;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Specifies that a CDI bean is enabled only when a custom condition evaluates
 * to {@code true}.
 * <p>
 * This annotation provides the low-level extension point for conditional bean
 * activation. It is intended for cases where {@link RequiresSetting} is not
 * sufficient.
 * </p>
 * <p>
 * When this annotation is declared on a bean class, producer method, or producer
 * field, the CDI container must evaluate the declared {@link Condition}
 * implementation before the bean participates in typesafe resolution.
 * </p>
 * <p>
 * If the declared condition evaluates to {@code false}, the annotated bean is
 * not enabled and must not participate in typesafe resolution.
 * </p>
 * <p>
 * This annotation supports a single condition. If a bean requires multiple
 * checks, compose them inside the declared {@link Condition} implementation.
 * </p>
 *
 * <pre>{@code
 * @RequiresCondition(BrazilCondition.class)
 * @ApplicationScoped
 * public class BrazilTaxCalculator implements TaxCalculator {
 * }
 * }</pre>
 *
 * @see RequiresSetting
 * @see Condition
 */
@Documented
@Retention(RUNTIME)
@Target({TYPE, METHOD, FIELD})
public @interface RequiresCondition {

    /**
     * The condition class that must evaluate to {@code true} for the annotated
     * bean to be enabled.
     *
     * @return the required condition class
     */
    Class<? extends Condition> value();
}