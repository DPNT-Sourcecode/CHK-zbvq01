package io.accelerate.solutions.CHK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutService {
    //given a list of products, calculate the total checkout price
    public Integer calculateTotal(List<Product> products) {
        Map<String, List> productMap = createProductMap(products);

        //for each product in the map, calculate the total price based on the unit price and the discounted price
        Integer totalPrice = 0;
        for (Map.Entry<String, List> entry : productMap.entrySet()) {
            String sku = entry.getKey();
            List<Product> productList = entry.getValue();
            Product product = productList.get(0);
            Integer unitPrice = product.getUnitPrice();
            Integer discountedPrice = product.getDiscountedPrice();
            Integer discountedMultiplier = product.getDiscountedMultiplier();
            Integer quantity = productList.size();
            if (discountedPrice != null && discountedMultiplier != null) {
                Integer discountedQuantity = quantity / discountedMultiplier;
                Integer remainingQuantity = quantity % discountedMultiplier;
                totalPrice += discountedQuantity * discountedPrice + remainingQuantity * unitPrice;
            } else {
                totalPrice += quantity * unitPrice;
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


