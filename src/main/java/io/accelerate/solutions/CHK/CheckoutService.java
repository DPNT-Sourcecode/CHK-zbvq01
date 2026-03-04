package io.accelerate.solutions.CHK;

import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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





    //given a list of products, calculate the total checkout price
    public Integer calculateTotalOld(List<Product> products) {
        Map<String, List> productMap = createProductMap(products);

        //for each product SKU in the map iterate over the list of those products, aggreate and the return the total price
        Integer totalPrice = 0;
        for (Map.Entry<String, List> entry : productMap.entrySet()) {
            for (Object product : entry.getValue()) {
                Product p = (Product) product;
                if (p.getDiscountedPrice() != null && p.getDiscountedMultiplier() != null) {
                    //if the product has a discount, calculate the total price for the discounted products of the same SKU and move to the next product SKU


                    Integer discountedPrice = p.getDiscountedPrice();
                    Integer discountedMultiplier = p.getDiscountedMultiplier();
                    Integer unitPrice = p.getUnitPrice();
                    Integer quantity = entry.getValue().size();

                    //calculate the total price for the discounted products
                    Integer discountedTotal = (quantity / discountedMultiplier) * discountedPrice;
                    Integer remainingTotal = (quantity % discountedMultiplier) * unitPrice;
                    totalPrice += discountedTotal + remainingTotal;
                    break;
                } else {
                    totalPrice += p.getUnitPrice();
                }
            }
        }
        return totalPrice;
    }

    //create a HashMap of products based on their SKU
    //hashmap should contains the String sku and the list of products
    private Map<String, List> createProductMap(List<Product> products) {
        Map<String, List> productMap = new HashMap<>();
        for (Product product : products) {
            if (!productMap.containsKey(product.getSku())) {
                productMap.put(product.getSku(), new ArrayList<>());
            }
            productMap.get(product.getSku()).add(product);
        }
        return productMap;
    }


}


