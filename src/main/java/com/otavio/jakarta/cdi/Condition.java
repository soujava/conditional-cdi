package com.otavio.jakarta.cdi;

/**
 * Determines whether a CDI bean should be enabled for the current deployment.
 * <p>
 * A condition is evaluated by the CDI container during bean discovery or
 * bootstrap, before the annotated bean participates in typesafe resolution.
 * </p>
 * <p>
 * A condition implementation should be deterministic and should only use
 * information that is safe to access during container bootstrap. Such
 * information is exposed through {@link ConditionContext}, for example bean
 * availability checks, class availability checks, classpath resource checks, or
 * other bootstrap-safe metadata.
 * </p>
 * <p>
 * A condition implementation must not depend on normal contextual bean instances
 * being available. At the time a condition is evaluated, the CDI container may
 * not have completed bean discovery, dependency validation, or contextual
 * instance creation.
 * </p>
 * <p>
 * The {@link ConditionContext} parameter should be used only for inspection.
 * Calling {@link #test(ConditionContext)} must not cause contextual bean
 * instances to be created.
 * </p>
 *
 * <pre>{@code
 * public class MetricsAvailableCondition implements Condition {
 *
 *     @Override
 *     public boolean test(ConditionContext context) {
 *         return context.hasClass("io.micrometer.core.instrument.MeterRegistry")
 *                 && context.hasBean(MetricsService.class);
 *     }
 * }
 * }</pre>
 *
 * @see ConditionContext
 * @see RequiresCondition
 * @see RequiresSetting
 */
@FunctionalInterface
public interface Condition {

    /**
     * Determines whether this condition matches the current deployment.
     * <p>
     * The supplied {@link ConditionContext} provides bootstrap-safe inspection
     * operations that may be used to evaluate the condition.
     * </p>
     *
     * @param context the condition evaluation context
     * @return {@code true} if the condition matches and the bean may be enabled;
     *         {@code false} otherwise
     */
    boolean test(ConditionContext context);
}