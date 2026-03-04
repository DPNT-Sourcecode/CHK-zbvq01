package io.accelerate.solutions.CHK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// this class have the service and layer
//it manages the rules priorities and initialze the catalog
public class CheckoutController{

    private final CheckoutService checkoutService;

    private final PricingRuleAggregator pricingRuleAggregator;

    public CheckoutController() {
        Map<String, Product> catalog = initializeCatalog();
        this.pricingRuleAggregator = new PricingRuleAggregator(catalog);
        this.checkoutService = new CheckoutService(new SKUParser(), catalog, pricingRuleAggregator.getPricingRules());
    }

    public Integer checkout(String skus) {
        return checkoutService.calculateTotal(skus);
    }

    private Map<String, Product> initializeCatalog() {
        Map<String, Product> catalog = new HashMap<>();
        catalog.put("A", new ProductImpl("A", 50));
        catalog.put("B", new ProductImpl("B", 30));
        catalog.put("C", new ProductImpl("C", 20));
        catalog.put("D", new ProductImpl("D", 15));
        catalog.put("E", new ProductImpl("E", 40));
        catalog.put("F", new ProductImpl("F", 10));
        catalog.put("G", new ProductImpl("G", 20));
        catalog.put("H", new ProductImpl("H", 10));
        catalog.put("I", new ProductImpl("I", 35));
        catalog.put("J", new ProductImpl("J", 60));
        catalog.put("K", new ProductImpl("K", 80));
        catalog.put("L", new ProductImpl("L", 90));
        catalog.put("M", new ProductImpl("M", 15));
        catalog.put("N", new ProductImpl("N", 40));
        catalog.put("O", new ProductImpl("O", 10));
        catalog.put("P", new ProductImpl("P", 50));
        catalog.put("Q", new ProductImpl("Q", 30));
        catalog.put("R", new ProductImpl("R", 50));
        catalog.put("S", new ProductImpl("S", 30));
        catalog.put("T", new ProductImpl("T", 20));
        catalog.put("U", new ProductImpl("U", 40));
        catalog.put("V", new ProductImpl("V", 50));
        catalog.put("W", new ProductImpl("W", 20));
        catalog.put("X", new ProductImpl("X", 90));
        catalog.put("Y", new ProductImpl("Y", 10));
        catalog.put("Z", new ProductImpl("Z", 50));

        return catalog;
    }
}
