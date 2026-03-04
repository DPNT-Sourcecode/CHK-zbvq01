package io.accelerate.solutions.CHK;

import lombok.Data;

import java.util.Map;

@Data
public class BuyXGetYFreeSameSkuPricingRule implements PricingRule {
    private final String sku;
    private final int buyQty;
    private final int freeQty;
    private final int priority;
    private final int unitPrice;


    @Override
    public int apply(PricingContext context) {
        int total = 0;
        int groupSize = buyQty + freeQty;

        while (context.getQuantity(sku)>= groupSize) {
            context.consume(sku, groupSize);

            //charge for buyQty only
            total += buyQty * unitPrice;
        }

        return total;
    }

    @Override
    public int getPriority() {
        return priority;
    }
}


