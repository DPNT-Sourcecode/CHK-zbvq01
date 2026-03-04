package io.accelerate.solutions.CHK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutService {
    //given a list of products, calculate the total checkout price
    public Integer calculateTotal(List<Product> products) {
        Integer total = 0;
        for (Product product : products) {
            if (product.getUnitPrice() == -1) {
                return -1;
            }
            if (product.getDiscountedPrice() != null && product.getDiscountedMultiplier() != null) {
                Integer discountedTotal = (products.size() / product.getDiscountedMultiplier()) * product.getDiscountedPrice();
                Integer remainingTotal = (products.size() % product.getDiscountedMultiplier()) * product.getUnitPrice();
                total += discountedTotal + remainingTotal;
            } else {
                total += product.getUnitPrice();
            }
        }
        return total;
    }

    //create a HashMap of products based on their SKU
    //hashmap should contains the String sku and the list of products
    public Map<String, List> createProductMap(List<Product> products) {
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

