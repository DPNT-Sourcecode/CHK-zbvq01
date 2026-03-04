package io.accelerate.solutions.CHK;

import lombok.Data;

@Data
public class SimpleSKUBundlePricingRule  implements PricingRule {

    private final String sku;
    private final int requiredQuantity;
    private final int bundlePrice;
    private int priority;

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public int apply(PricingContext context) {
        return 0;
    }

}
