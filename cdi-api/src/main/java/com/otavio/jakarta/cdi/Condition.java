package com.otavio.jakarta.cdi;

/**
 * Determines whether a CDI bean should be enabled for the current deployment.
 * <p>
 * A condition is evaluated by the CDI container during bean discovery or
 * bootstrap, before the annotated bean participates in typesafe resolution.
 * </p>
 * <p>
 * A condition implementation must not depend on normal contextual bean
 * instances being available. At the time a condition is evaluated, the CDI
 * container may not have completed bean discovery, dependency validation, or
 * contextual instance creation.
 * </p>
 * <p>
 * Implementations should be deterministic and should only use information made
 * available by the container for conditional evaluation, such as runtime
 * settings, environment data, deployment metadata, classpath checks, or other
 * bootstrap-safe information.
 * </p>
 *
 * <pre>{@code
 * public class BrazilCondition implements Condition {
 *
 *     @Override
 *     public boolean matches(ConditionContext context) {
 *         return "BR".equals(context.setting("app.country").orElse(null));
 *     }
 * }
 * }</pre>
 *
 * @see RequiresCondition
 * @see RequiresSetting
 */
public interface Condition {

    /**
     * Determines whether the condition matches the current deployment.
     *
     * @param context the condition evaluation context
     * @return {@code true} if the condition matches and the bean may be enabled;
     *         {@code false} otherwise
     */
    boolean matches(ConditionContext context);
}