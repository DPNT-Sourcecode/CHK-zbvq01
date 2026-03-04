package io.accelerate.solutions.CHK;

import lombok.Data;


@Data
public class BuyXGetYFreeSameSkuPricingRule implements PricingRule {
    private final String sku;
    private final int buyQty;
    private final int freeQty;
    private final int unitPrice;
    private final int priority;

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
