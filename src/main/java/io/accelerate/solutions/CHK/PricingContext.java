package io.accelerate.solutions.CHK;

import java.util.Map;

public class PricingContext {


    private final Map<String, Integer> quantities;

    public PricingContext(Map<String, Integer> quantities) {
        this.quantities = quantities;
    }

    public int getQuantity(String productCode) {
        return quantities.getOrDefault(productCode, 0);
    }

    public boolean contains(String sku) {
        return quantities.containsKey(sku);
    }

    public void consume(String sku, int amount) {
        int current = quantities.get(sku);
        quantities.put(sku, current - amount);
    }

    public Map<String, Integer> remaining() {
        return quantities;
    }
}
