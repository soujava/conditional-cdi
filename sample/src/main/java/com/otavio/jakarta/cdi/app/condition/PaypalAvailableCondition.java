package com.otavio.jakarta.cdi.app.condition;


import com.otavio.jakarta.cdi.Condition;

public class PaypalAvailableCondition implements Condition {

    @Override
    public boolean test() {
       return !PaymentGateway.STRIPE_AVAILABLE;
    }
}