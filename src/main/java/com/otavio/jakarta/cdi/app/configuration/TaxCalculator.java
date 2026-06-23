package com.otavio.jakarta.cdi.app.configuration;

import java.math.BigDecimal;

public interface TaxCalculator {

    BigDecimal calculate(BigDecimal amount);
}
