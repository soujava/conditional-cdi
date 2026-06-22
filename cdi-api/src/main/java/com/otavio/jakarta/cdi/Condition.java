package com.otavio.jakarta.cdi;

/**
 * Determines whether a CDI bean should be enabled for the current deployment.
 * <p>
 * A condition is evaluated by the CDI container during bean discovery or
 * bootstrap, before the annotated bean participates in typesafe resolution.
 * </p>
 * <p>
 * A condition implementation should be deterministic and should only use
 * information that is safe to access during container bootstrap, such as system
 * properties, environment variables, classpath checks, deployment metadata, or
 * other bootstrap-safe mechanisms.
 * </p>
 * <p>
 * A condition implementation must not depend on normal contextual bean instances
 * being available. At the time a condition is evaluated, the CDI container may
 * not have completed bean discovery, dependency validation, or contextual
 * instance creation.
 * </p>
 *
 * <pre>{@code
 * public class BrazilCondition implements Condition {
 *
 *     @Override
 *     public boolean test() {
 *         return "BR".equals(System.getProperty("app.country"))
 *                 || "BR".equals(System.getenv("APP_COUNTRY"));
 *     }
 * }
 * }</pre>
 *
 * @see RequiresCondition
 * @see RequiresSetting
 */
@FunctionalInterface
public interface Condition {

    /**
     * Determines whether this condition matches the current deployment.
     *
     * @return {@code true} if the condition matches and the bean may be enabled;
     *         {@code false} otherwise
     */
    boolean test();
}