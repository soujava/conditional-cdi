package com.otavio.jakarta.cdi.app.configuration;

import com.otavio.jakarta.cdi.RequiresSetting;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;

@RequiresSetting(name = "app.country", value = "PT")
@ApplicationScoped
public class PortugalTaxCalculator implements TaxCalculator {

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return amount.multiply(new BigDecimal("0.23"));
    }
}