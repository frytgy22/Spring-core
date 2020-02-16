package org.lebedeva;

import java.util.Optional;

public interface AssistantMethods {

    static String getArithmeticSymbol(String symbol) {
        return "%2B".equals(symbol) ? "+" :
                "%2F".equals(symbol) ? "/" : symbol;
    }

    static Optional<Double> getDouble(String data) {
        if (data != null) {
            try {
                return Optional.of(Double.parseDouble(data.replaceAll(",", ".")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }
}
