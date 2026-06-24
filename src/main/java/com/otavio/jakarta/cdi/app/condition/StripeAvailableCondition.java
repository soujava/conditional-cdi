package com.otavio.jakarta.cdi.app.condition;


import com.otavio.jakarta.cdi.Condition;
import com.otavio.jakarta.cdi.ConditionContext;

public class StripeAvailableCondition implements Condition {

    @Override
    public boolean test(ConditionContext context) {
       return PaymentGateway.STRIPE_AVAILABLE;
    }
}