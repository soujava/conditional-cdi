package com.otavio.jakarta.cdi.app.condition;

import jakarta.enterprise.inject.se.SeContainerInitializer;

public class App {

    public static void main(String[] args) {

        try(var container = SeContainerInitializer.newInstance().initialize()) {
            PaymentGateway paymentGateway = container.select(PaymentGateway.class).get();
            paymentGateway.pay();
        }
    }
}
