package io.accelerate.solutions.CHK;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PricingRuleAggregator {

    private final Map<String, Product> catalog;

    public PricingRuleAggregator(Map<String, Product> catalog) {
        this.catalog = catalog;
    }

    public List<PricingRule> getPricingRules() {
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



        //grouping bundle offer between multi sku and single sku
        // should have priority 350
        rules.add(new BuyXFromGroupYPricingRule(
                Set.of("S", "T", "X", "Y", "Z"), //SKUs in the group
                3, //buy 3
                45, //for 3 items from the group
                catalog,
                350
        ));





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

        //V Offer
        rules.add(new SimpleSkuBundlePricingRule(
                "V", //SKU
                3, //required qty
                130, //price for 3 v's
                300)); //priority
        //V Offer
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

        //U offer buy 3 get 1 free (need 3 U's to get the discount)
        rules.add(new BuyXGetYFreeSameSkuPricingRule(
                "U",
                3,
                1,
                catalog.get("U").getUnitPrice(),
                250));



        //fallback to unit price for any remaining items
        rules.add(new UnitPricePricingRule(catalog));

        return rules;
    }
}
