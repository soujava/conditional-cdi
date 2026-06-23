/**
 * Provides annotations and contracts for conditional CDI bean activation.
 * <p>
 * This package defines a small API that allows CDI beans to be enabled only
 * when a condition or runtime setting matches the current deployment.
 * </p>
 * <p>
 * Conditional activation is different from bean discovery. The annotations in
 * this package do not define the business role of a bean and should not replace
 * regular CDI bean-defining annotations such as {@code @ApplicationScoped},
 * {@code @RequestScoped}, or other CDI scopes. A bean must still be discovered
 * by CDI through the normal bean discovery rules before conditional activation
 * is evaluated.
 * </p>
 * <p>
 * The {@link com.otavio.jakarta.cdi.RequiresSetting} annotation enables a bean
 * only when a named runtime setting resolves to the expected value. The source
 * of the setting is implementation-defined and may include system properties,
 * environment variables, configuration files, deployment descriptors, or other
 * runtime configuration mechanisms.
 * </p>
 * <p>
 * The {@link com.otavio.jakarta.cdi.RequiresCondition} annotation enables a bean
 * only when the declared {@link com.otavio.jakarta.cdi.Condition} evaluates to
 * {@code true}. This is intended for cases where a simple setting comparison is
 * not sufficient.
 * </p>
 * <p>
 * A bean whose requirement does not match is not enabled and must not
 * participate in CDI typesafe resolution.
 * </p>
 *
 * <h2>Example using a runtime setting</h2>
 *
 * <pre>{@code
 * @RequiresSetting(name = "app.country", value = "BR")
 * @ApplicationScoped
 * public class BrazilTaxCalculator implements TaxCalculator {
 * }
 * }</pre>
 *
 * <h2>Example using a custom condition</h2>
 *
 * <pre>{@code
 * public class BrazilCondition implements Condition {
 *
 *     @Override
 *     public boolean test() {
 *         return "BR".equals(System.getProperty("app.country"));
 *     }
 * }
 *
 * @RequiresCondition(BrazilCondition.class)
 * @ApplicationScoped
 * public class BrazilTaxCalculator implements TaxCalculator {
 * }
 * }</pre>
 *
 * @see com.otavio.jakarta.cdi.RequiresSetting
 * @see com.otavio.jakarta.cdi.RequiresCondition
 * @see com.otavio.jakarta.cdi.Condition
 */
package com.otavio.jakarta.cdi;