/**
 * Provides portable CDI extensions for conditional bean activation.
 * <p>
 * This package contains extension implementations that evaluate conditional
 * activation annotations before beans participate in CDI typesafe resolution.
 * The extensions observe CDI bootstrap events and veto beans whose requirements
 * do not match the current deployment.
 * </p>
 * <p>
 * The {@link com.otavio.jakarta.cdi.extension.RequiresConditionExtension}
 * evaluates {@link com.otavio.jakarta.cdi.RequiresCondition}. If the declared
 * {@link com.otavio.jakarta.cdi.Condition} evaluates to {@code false}, the
 * corresponding bean is vetoed and does not participate in typesafe resolution.
 * </p>
 * <p>
 * The {@link com.otavio.jakarta.cdi.extension.RequiresSettingExtension}
 * evaluates {@link com.otavio.jakarta.cdi.RequiresSetting}. If the named
 * runtime setting does not resolve to the expected value, the corresponding
 * bean is vetoed and does not participate in typesafe resolution.
 * </p>
 * <p>
 * This implementation currently resolves settings through Eclipse MicroProfile
 * Config. That is an implementation decision made by this provider and is not
 * part of the conditional activation API contract. The annotations themselves
 * should not require CDI implementations or applications to depend on
 * MicroProfile Config.
 * </p>
 * <p>
 * A different provider may resolve settings through another mechanism, such as
 * system properties, environment variables, deployment descriptors,
 * configuration files, custom configuration sources, or another runtime
 * configuration API.
 * </p>
 * <p>
 * If Jakarta Config becomes available as a standard Jakarta EE configuration
 * mechanism, this extension package can be updated to use Jakarta Config as the
 * preferred setting resolution strategy.
 * </p>
 * <p>
 * These extensions are discovered through the Java {@link java.util.ServiceLoader}
 * mechanism. The implementation must provide the following service provider
 * configuration file:
 * </p>
 *
 * <pre>{@code
 * META-INF/services/jakarta.enterprise.inject.spi.Extension
 * }</pre>
 *
 * <p>
 * The file should contain the fully qualified names of the extension classes:
 * </p>
 *
 * <pre>{@code
 * com.otavio.jakarta.cdi.extension.RequiresConditionExtension
 * com.otavio.jakarta.cdi.extension.RequiresSettingExtension
 * }</pre>
 *
 * <p>
 * These extensions do not make a type discoverable as a CDI bean. They only
 * apply conditional activation to beans that are already discovered by CDI
 * through normal bean discovery rules.
 * </p>
 *
 * @see com.otavio.jakarta.cdi.extension.RequiresConditionExtension
 * @see com.otavio.jakarta.cdi.extension.RequiresSettingExtension
 * @see com.otavio.jakarta.cdi.RequiresCondition
 * @see com.otavio.jakarta.cdi.RequiresSetting
 * @see com.otavio.jakarta.cdi.Condition
 */
package com.otavio.jakarta.cdi.extension;