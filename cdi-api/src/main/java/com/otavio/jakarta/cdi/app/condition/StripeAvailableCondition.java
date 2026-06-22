package com.otavio.jakarta.cdi.app.condition;


import com.otavio.jakarta.cdi.Condition;

public class StripeAvailableCondition implements Condition {

    @Override
    public boolean test() {
        try {
            Class.forName("com.stripe.Stripe");
            return true;
        } catch (ClassNotFoundException exception) {
            return false;
        }
    }
}