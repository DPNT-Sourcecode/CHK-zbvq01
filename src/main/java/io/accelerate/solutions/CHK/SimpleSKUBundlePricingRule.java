package io.accelerate.solutions.CHK;

import lombok.Data;

@Data

// 5A for 200
// 3A for 130
// this is to handle when large bundle have higher priority
public class SimpleSKUBundlePricingRule  implements PricingRule {

    private final String sku;
    private final int requiredQuantity;
    private final int bundlePrice;
    private int priority;

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public int apply(PricingContext context) {
        int total = 0;
        while (context.getQuantity(sku) >= requiredQuantity) {
            context.consume(sku, requiredQuantity);
            total += bundlePrice;
        }
        return total;
    }

}
