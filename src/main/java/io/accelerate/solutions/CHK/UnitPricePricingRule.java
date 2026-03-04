package io.accelerate.solutions.CHK;

import lombok.Data;

import java.util.Map;

@Data
// this is for the lowest priority rule, after all other are processed
public class UnitPricePricingRule implements PricingRule {

    private final Map<String, Product> catalog;
    private final int priority = Integer.MIN_VALUE;

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public int apply(PricingContext context) {
        int total = 0;

        for (Map.Entry<String, Integer> entry : context.remaining().entrySet()) {
            String sku = entry.getKey();
            int quantity = entry.getValue();

            if (quantity > 0) {
                total += quantity * catalog.get(sku).getUnitPrice();
                context.consume(sku, quantity);
            }
        }
        return total;
    }
}

