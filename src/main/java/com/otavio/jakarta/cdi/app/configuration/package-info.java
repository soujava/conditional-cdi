/**
 * Demonstrates conditional CDI bean activation using {@code @RequiresSetting}.
 * <p>
 * This package contains a small CDI SE sample where two implementations of
 * {@link com.otavio.jakarta.cdi.app.configuration.TaxCalculator} are available:
 * one for Brazil and one for Portugal.
 * </p>
 * <p>
 * Both implementations are regular CDI beans because they declare
 * {@code @ApplicationScoped}. The {@code @RequiresSetting} annotation does not
 * make the classes discoverable by CDI; it only decides whether already
 * discovered beans remain enabled.
 * </p>
 * <p>
 * The {@link com.otavio.jakarta.cdi.app.configuration.BrazilTaxCalculator} bean
 * is enabled only when the runtime setting {@code app.country} resolves to
 * {@code BR}. The {@link com.otavio.jakarta.cdi.app.configuration.PortugalTaxCalculator}
 * bean is enabled only when the same setting resolves to {@code PT}.
 * </p>
 * <p>
 * In this sample, the application sets {@code app.country} to {@code PT} before
 * bootstrapping the CDI SE container. Therefore, the CDI extension vetoes
 * {@code BrazilTaxCalculator} and keeps {@code PortugalTaxCalculator}. The
 * application can then resolve {@code TaxCalculator} without ambiguity.
 * </p>
 *
 * <pre>{@code
 * System.setProperty("app.country", "PT");
 *
 * try (var container = SeContainerInitializer.newInstance().initialize()) {
 *     TaxCalculator taxCalculator = container.select(TaxCalculator.class).get();
 *     System.out.println("Tax for 100: "
 *             + taxCalculator.calculate(new BigDecimal("100")));
 * }
 * }</pre>
 *
 * <p>
 * The expected output is:
 * </p>
 *
 * <pre>{@code
 * Tax for 100: 23.00
 * }</pre>
 *
 * <p>
 * To switch the active implementation in this sample, change the runtime
 * setting {@code app.country} to {@code BR}. The extension will then keep
 * {@code BrazilTaxCalculator} and veto {@code PortugalTaxCalculator}.
 * </p>
 *
 * @see com.otavio.jakarta.cdi.RequiresSetting
 * @see com.otavio.jakarta.cdi.app.configuration.TaxCalculator
 * @see com.otavio.jakarta.cdi.app.configuration.BrazilTaxCalculator
 * @see com.otavio.jakarta.cdi.app.configuration.PortugalTaxCalculator
 */
package com.otavio.jakarta.cdi.app.configuration;