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
        Map<String, Product> catalog = initializeCatalog();
        this.checkoutService = new CheckoutService(new SKUParser(), catalog, initializePricingRules(catalog));
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

    private List<PricingRule> initializePricingRules(Map<String, Product> catalog) {
        List<PricingRule> rules = new ArrayList<>();
        //multi SKU RULE first
        //E's multi-buy discount), price of 2E only
        rules.add(new MultiSkuBundlePricingRule(
                Map.of("E", 2, "B", 1),
                2 * 40,
                400));

        //single SKU bundle larger first
        // A's multi-buy discount with higher priority
        rules.add(new SimpleSkuBundlePricingRule(
                "A", //SKU
                5, //required qty
                200, //price for 5 A's
                300)); //priority

        rules.add(new SimpleSkuBundlePricingRule(
                "A", //SKU
                3, //required qty
                130, //price for 5 A's
                200));

        //B offer
        rules.add(new SimpleSkuBundlePricingRule(
                "B", //SKU
                2, //required qty
                45, //price for 4 B's
                200)); //priority

        //fallback to unit price for any remaining items
        rules.add(new UnitPricePricingRule(catalog));

        return rules;
    }
}

