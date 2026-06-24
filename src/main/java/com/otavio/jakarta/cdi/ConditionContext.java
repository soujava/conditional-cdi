package com.otavio.jakarta.cdi;

import jakarta.enterprise.util.TypeLiteral;

import java.lang.annotation.Annotation;

/**
 * Provides bootstrap-safe information for evaluating conditional bean activation.
 * <p>
 * A {@code ConditionContext} is passed to a {@link Condition} when the CDI
 * container evaluates whether a bean should be enabled for the current
 * deployment. The context exposes a small set of operations that can be safely
 * used during bean discovery or container bootstrap.
 * </p>
 * <p>
 * The methods in this interface are intended for discovery-time checks, such as
 * verifying whether another bean exists, whether a class is available, or
 * whether a classpath resource is present.
 * </p>
 * <p>
 * A {@code ConditionContext} must not expose normal contextual bean instances.
 * Conditions should not depend on bean creation, injection, or runtime state
 * that is only available after the container has completed bootstrap.
 * </p>
 *
 * <pre>{@code
 * public class MetricsEnabledCondition implements Condition {
 *
 *     @Override
 *     public boolean test(ConditionContext context) {
 *         return context.hasClass(MeterRegistry.class)
 *                 && context.hasBean(MetricsService.class);
 *     }
 * }
 * }</pre>
 *
 * @see Condition
 * @see RequiresCondition
 * @see RequiresSetting
 */
public interface ConditionContext {

    /**
     * Determines whether a bean with the given bean type and qualifiers is
     * available to the current deployment.
     * <p>
     * This method checks bean availability using CDI typesafe resolution rules.
     * The method returns {@code true} when at least one bean matches the given
     * type and qualifiers.
     * </p>
     * <p>
     * The result should be used only to decide conditional activation. Calling
     * this method must not cause contextual bean instances to be created.
     * </p>
     *
     * @param type the required bean type
     * @param qualifiers the required qualifiers; if none are provided, the
     *        default CDI qualifier behavior applies
     * @param <T> the required bean type
     * @return {@code true} if a matching bean is available; {@code false}
     *         otherwise
     */
    <T> boolean hasBean(Class<T> type, Annotation... qualifiers);

    /**
     * Determines whether a bean with the given parameterized bean type and
     * qualifiers is available to the current deployment.
     * <p>
     * This overload is useful when the required bean type cannot be represented
     * precisely by a {@link Class}, such as parameterized types.
     * </p>
     *
     * <pre>{@code
     * boolean available = context.hasBean(new TypeLiteral<List<String>>() {});
     * }</pre>
     * <p>
     * The method checks bean availability using CDI typesafe resolution rules.
     * It returns {@code true} when at least one bean matches the given type and
     * qualifiers.
     * </p>
     * <p>
     * The result should be used only to decide conditional activation. Calling
     * this method must not cause contextual bean instances to be created.
     * </p>
     *
     * @param type the required parameterized bean type
     * @param qualifiers the required qualifiers; if none are provided, the
     *        default CDI qualifier behavior applies
     * @param <T> the required bean type
     * @return {@code true} if a matching bean is available; {@code false}
     *         otherwise
     */
    <T> boolean hasBean(TypeLiteral<T> type, Annotation... qualifiers);

    /**
     * Determines whether the given class is available to the current deployment.
     * <p>
     * This method is intended for checking optional dependencies or integration
     * classes. Since the method receives a {@link Class}, the class has already
     * been loaded by the caller.
     * </p>
     * <p>
     * If the goal is to check whether a class exists without linking directly to
     * it, a future overload accepting a fully qualified class name may be more
     * appropriate.
     * </p>
     *
     * @param type the class to check
     * @param <T> the class type
     * @return {@code true} if the class is available to the deployment;
     *         {@code false} otherwise
     */
    <T> boolean hasClass(Class<T> type);

    /**
     * Determines whether a resource exists on the deployment classpath.
     * <p>
     * The {@code path} should be expressed as a classpath-relative resource
     * name, without a leading slash.
     * </p>
     *
     * <pre>{@code
     * boolean present = context.hasClasspathResource(
     *         "META-INF/services/jakarta.enterprise.inject.spi.Extension");
     * }</pre>
     *
     * @param path the classpath-relative resource path
     * @return {@code true} if the resource is available on the classpath;
     *         {@code false} otherwise
     */
    boolean hasClasspathResource(String path);
}
