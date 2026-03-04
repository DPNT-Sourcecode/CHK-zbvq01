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

    private List<PricingRule> initializePricingRules(Map<String, Product> catalog) {
        List<PricingRule> rules = new ArrayList<>();
        //multi SKU RULE first
        //E's multi-buy discount), price of 2E only
        rules.add(new MultiSkuBundlePricingRule(
                Map.of("E", 2, "B", 1),
                2 * 40,
                400));
        //N's multi-buy discount), price of 3N only
        rules.add(new MultiSkuBundlePricingRule(
                Map.of("N", 3, "M", 1),
                3 * 40,
                400));
        //R's multi-buy discount), price of 3R only
        rules.add(new MultiSkuBundlePricingRule(
                Map.of("R", 3, "Q", 1),
                3 * 50,
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

        //H Offer
        rules.add(new SimpleSkuBundlePricingRule(
                "H", //SKU
                10, //required qty
                80, //price for 10 H's
                300)); //priority

        rules.add(new SimpleSkuBundlePricingRule(
                "H", //SKU
                5, //required qty
                45, //price for 5 H's
                200));

        //H Offer
        rules.add(new SimpleSkuBundlePricingRule(
                "V", //SKU
                3, //required qty
                130, //price for 3 v's
                300)); //priority

        rules.add(new SimpleSkuBundlePricingRule(
                "V", //SKU
                2, //required qty
                90, //price for 2 V's
                200));



        //B offer
        rules.add(new SimpleSkuBundlePricingRule(
                "B", //SKU
                2, //required qty
                45, //price for 4 B's
                200)); //priority
        //K offer
        rules.add(new SimpleSkuBundlePricingRule(
                "K", //SKU
                2, //required qty
                150, //price for 2 K's
                200)); //priority
        //P offer
        rules.add(new SimpleSkuBundlePricingRule(
                "P", //SKU
                5, //required qty
                200, //price for 5 Ps
                200)); //priority
        //Q offer
        rules.add(new SimpleSkuBundlePricingRule(
                "Q", //SKU
                3, //required qty
                80, //price for 3 Qs
                200)); //priority






        //F offer buy 2 get 1 free (need 3 F's to get the discount)
        rules.add(new BuyXGetYFreeSameSkuPricingRule(
                "F",
                2,
                1,
                catalog.get("F").getUnitPrice(),
                250));



        //fallback to unit price for any remaining items
        rules.add(new UnitPricePricingRule(catalog));

        return rules;
    }
}

