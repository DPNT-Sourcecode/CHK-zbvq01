package io.accelerate.solutions.CHK;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Data
public class BuyXFromGroupYPricingRule implements  PricingRule {
    private final Set<String> groupSkus;
    private final int groupSize;
    private final int bundlePrice;
    private final Map<String, Product> catalog;
    private final int priority;

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public int apply(PricingContext context) {

        //iterate over grouped items from groupSKU
        // when we reach the groupsize we apply the bundle price

        int total = 0;
        while(countTotalGroupItems(context) >= groupSize) {

            //create a list of sorted, so we can apply bigger discounts first
            List<String> sortedSkus = groupSkus.stream()
                    .filter(sku -> context.getQuantity(sku) > 0)
                    .sorted((sku1, sku2) ->
                            Integer.compare(
                                    catalog.get(sku2).getUnitPrice(),
                                    catalog.get(sku1).getUnitPrice()
                            )
                    ).toList(); // Sort by price descending

            //now we iterate over the sorted SKUs and apply the bundle price until we reach the group size
            int consumed = 0;
            for (String sku : sortedSkus) {
                while(context.getQuantity(sku) > 0 && consumed < groupSize) {
                    context.consume(sku, 1);
                    consumed++;
                }

                if (consumed >= groupSize) {
                    break; // Stop if we've consumed enough for the bundle
                }
            }

            //apply the bundle price for the consumed items
            total += bundlePrice;
        }
        return total;
    }

    // Calculate how many complete bundles can be formed from the group SKUs
    private int countTotalGroupItems(PricingContext context) {
        return groupSkus.stream()
                .mapToInt(context::getQuantity)
                .sum();
    }
}
