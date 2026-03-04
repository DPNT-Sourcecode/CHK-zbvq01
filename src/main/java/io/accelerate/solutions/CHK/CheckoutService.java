package io.accelerate.solutions.CHK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutService {
    //given a list of products, calculate the total checkout price
    public Integer calculateTotal(List<Product> products) {
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
