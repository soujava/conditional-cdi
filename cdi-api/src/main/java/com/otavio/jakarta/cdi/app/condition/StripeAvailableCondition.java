package com.otavio.jakarta.cdi.app.condition;


import com.otavio.jakarta.cdi.Condition;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StripeAvailableCondition implements Condition {

    @Override
    public boolean test() {
       return PaymentGateway.STRIPE_AVAILABLE;
    }
}