package com.otavio.jakarta.cdi.app.condition;

import com.otavio.jakarta.cdi.RequiresCondition;
import jakarta.enterprise.context.ApplicationScoped;

@RequiresCondition(StripeAvailableCondition.class)
@ApplicationScoped
public class PaypalPaymentGateway implements PaymentGateway {

    @Override
    public void pay() {
        System.out.println("Processing payment with Paypal");
    }
}