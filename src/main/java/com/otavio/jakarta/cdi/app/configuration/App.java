package com.otavio.jakarta.cdi.app.configuration;

public class App {

    public static void main(String[] args) {
        System.setProperty("app.country", "BR");
        try (var container = jakarta.enterprise.inject.se.SeContainerInitializer.newInstance().initialize()) {
            TaxCalculator taxCalculator = container.select(TaxCalculator.class).get();
            System.out.println("Tax for 100: " + taxCalculator.calculate(new java.math.BigDecimal("100")));
        }
    }
}
