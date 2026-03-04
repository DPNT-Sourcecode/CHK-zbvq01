package io.accelerate.solutions.CHK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// this class have the service and layer
//it manages the rules priorities and initialze the catalog
public class CheckoutController{

    private final CheckoutService checkoutService;

    public CheckoutController() {
        this.checkoutService = new CheckoutService(new SKUParser(), initializeCatalog(), initializePricingRules());
    }

    public Integer checkout(String skus) {
        return checkoutService.calculateTotal(skus);
    }

    private Map<String, Product> initializeCatalog() {
        Map<String, Product> catalog = new HashMap<>();
        catalog.put("A", new ProductImpl("A", 50, 130, 3));
        catalog.put("B", new ProductImpl("B", 30, 45, 2));
        catalog.put("C", new ProductImpl("C", 20, null, null));
        catalog.put("D", new ProductImpl("D", 15, null, null));
        catalog.put("E", new ProductImpl("E", 40, null, null));
        return catalog;
    }

    private List<PricingRule> initializePricingRules() {
        List<PricingRule> rules = new ArrayList<>();
        //multi SKU RULE first
        rules.add(new Mu)

        rules.add(new MultiBuyDiscountRule(1)); // A's multi-buy discount
        rules.add(new MultiBuyDiscountRule(2)); // B's multi-buy discount
        return rules;
    }
}

