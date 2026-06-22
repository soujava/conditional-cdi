package com.otavio.jakarta.cdi.app;

import com.otavio.jakarta.cdi.RequiresSetting;
import jakarta.enterprise.context.ApplicationScoped;

@RequiresSetting(name = "payment.provider", value = "paypal")
@ApplicationScoped
public class PaypalPaymentGateway implements PaymentGateway {

    @Override
    public void pay() {
        System.out.println("Processing payment with PayPal");
    }
}