package com.otavio.jakarta.cdi.app.condition;

public interface PaymentGateway {
    boolean STRIPE_AVAILABLE = false;
    void pay();
}