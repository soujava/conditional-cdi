/**
 * Demonstrates conditional CDI bean activation using {@code @RequiresCondition}.
 * <p>
 * This package contains a small CDI SE sample where two implementations of
 * {@link com.otavio.jakarta.cdi.app.condition.PaymentGateway} are available:
 * one for Stripe and one for Paypal.
 * </p>
 * <p>
 * Both implementations are regular CDI beans because they declare
 * {@code @ApplicationScoped}. The conditional annotations do not make the
 * classes discoverable by CDI; they only decide whether already-discovered beans
 * remain enabled.
 * </p>
 * <p>
 * The {@link com.otavio.jakarta.cdi.app.condition.StripePaymentGateway} bean is
 * annotated with {@code @RequiresCondition(StripeAvailableCondition.class)}.
 * The {@link com.otavio.jakarta.cdi.app.condition.PaypalPaymentGateway} bean is
 * annotated with {@code @RequiresCondition(PaypalAvailableCondition.class)}.
 * </p>
 * <p>
 * The conditions use the {@code STRIPE_AVAILABLE} flag declared in
 * {@link com.otavio.jakarta.cdi.app.condition.PaymentGateway}. When the flag is
 * {@code true}, the Stripe condition matches and the Paypal condition does not.
 * When the flag is {@code false}, the Paypal condition matches and the Stripe
 * condition does not.
 * </p>
 * <p>
 * In this sample, {@code STRIPE_AVAILABLE} is {@code false}. Therefore, the CDI
 * extension vetoes {@code StripePaymentGateway} and keeps
 * {@code PaypalPaymentGateway}. The application can then resolve
 * {@code PaymentGateway} without ambiguity.
 * </p>
 *
 * <pre>{@code
 * try (var container = SeContainerInitializer.newInstance().initialize()) {
 *     PaymentGateway paymentGateway = container.select(PaymentGateway.class).get();
 *     paymentGateway.pay();
 * }
 * }</pre>
 *
 * <p>
 * The expected output is:
 * </p>
 *
 * <pre>{@code
 * Processing payment with Paypal
 * }</pre>
 *
 * <p>
 * To switch the active implementation in this sample, change
 * {@code STRIPE_AVAILABLE} to {@code true}. The extension will then keep
 * {@code StripePaymentGateway} and veto {@code PaypalPaymentGateway}.
 * </p>
 *
 * @see com.otavio.jakarta.cdi.RequiresCondition
 * @see com.otavio.jakarta.cdi.Condition
 * @see com.otavio.jakarta.cdi.app.condition.PaymentGateway
 * @see com.otavio.jakarta.cdi.app.condition.StripeAvailableCondition
 * @see com.otavio.jakarta.cdi.app.condition.PaypalAvailableCondition
 * @see com.otavio.jakarta.cdi.app.condition.StripePaymentGateway
 * @see com.otavio.jakarta.cdi.app.condition.PaypalPaymentGateway
 */
package com.otavio.jakarta.cdi.app.condition;