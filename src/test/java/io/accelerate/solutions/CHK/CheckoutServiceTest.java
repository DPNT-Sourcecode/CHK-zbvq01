package io.accelerate.solutions.CHK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CheckoutServiceTest {
    private CheckoutService checkoutService;

    @BeforeEach
    public void setUp() {

        Map<String, Product> catalog = new HashMap<>();
        catalog.put("A", new ProductImpl("A", 50, 130, 3));
        catalog.put("B", new ProductImpl("B", 30, 45, 2));
        catalog.put("C", new ProductImpl("C", 20, null, null));
        catalog.put("D", new ProductImpl("D", 15, null, null));
        catalog.put("E", new ProductImpl("E", 40, null, null));



        checkoutService = new CheckoutService(new SKUParser(), catalog, initializePricingRules(catalog));
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



    @Test
    public void testCalculateTotal() {
        Integer totalPrice = checkoutService.calculateTotal("ABCD");
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(115));
    }

    @Test
    public void testSimpleProductA() {
        Integer totalPrice = checkoutService.calculateTotal("AA");
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(100));
    }

    @Test
    public void testCombinationOfProducts() {
        Integer totalPrice = checkoutService.calculateTotal("ABC");
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(100));
    }


    @Test
    public void testMultipleEqualsProducts() {
        Integer totalPrice = checkoutService.calculateTotal("AAA");
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(130));
    }

    @Test
    public void testMultipleEqualsProductsPlus1() {
        Integer totalPrice = checkoutService.calculateTotal("AAAA");
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(180));
    }

    @Test
    public void testTwoDifferentProductsWithDiscount() {
        Integer totalPrice = checkoutService.calculateTotal("AAABB");
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(175));
    }


    @Test
    public void testProducsWithNoDiscount() {
        Integer totalPrice = checkoutService.calculateTotal("CD");
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(35));
    }




}


