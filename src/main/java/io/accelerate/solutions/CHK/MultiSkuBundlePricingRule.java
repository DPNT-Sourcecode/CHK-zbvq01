package io.accelerate.solutions.CHK;

import lombok.Data;

import java.util.Map;

@Data
public class MultiSkuBundlePricingRule implements PricingRule {
    private final Map<String, Integer> requiredItems;
    private final int bundlePrice;
    private final int priority;

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public int apply(PricingContext context) {
        int total = 0;


        while (canApply(context)) {
            for (Map.Entry<String, Integer> entry : requiredItems.entrySet()) {
                context.consume(entry.getKey(), entry.getValue());
            }
            total += bundlePrice;
        }

        return total;
    }


    private boolean canApply(PricingContext context) {
        for (Map.Entry<String, Integer> entry : requiredItems.entrySet()) {
            if (context.getQuantity(entry.getKey()) < entry.getValue()) {
                return false;
            }
        }

        return true;
    }


}

