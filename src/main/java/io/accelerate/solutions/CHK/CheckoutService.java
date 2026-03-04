package io.accelerate.solutions.CHK;

import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Data
public class CheckoutService {

    private final SKUParser skuParser;
    private final Map<String, Product> productCatalog;
    private final List<PricingRule> pricingRules;


    public Integer calculateTotal(String skuStream) {
        //empty cart
        if (skuStream == null || skuStream.isEmpty()) {
            return 0;
        }

        Map<String, Integer> parsed = skuParser.parse(skuStream);

        // validate SKUS
        for (String sku : parsed.keySet()) {
            if (!productCatalog.containsKey(sku)) {
                return -1;
            }
        }

        //initialize context
        PricingContext context = new PricingContext(parsed);

        //apply pricing rules based on their priority
        List<PricingRule> orderedRules = new ArrayList<>(pricingRules);

        // sort the pricing rules based on their priority in descending order
        orderedRules.sort(Comparator.comparing(PricingRule::getPriority).reversed());

        //finally calculate the total
        int total = 0;
        for(PricingRule rule : orderedRules) {
            total += rule.apply(context);
        }

        return total;
    }
}
