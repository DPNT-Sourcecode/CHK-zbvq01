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
        catalog.put("A", new ProductImpl("A", 50));
        catalog.put("B", new ProductImpl("B", 30));
        catalog.put("C", new ProductImpl("C", 20));
        catalog.put("D", new ProductImpl("D", 15));
        catalog.put("E", new ProductImpl("E", 40));
        catalog.put("F", new ProductImpl("F", 10));

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

    @Test
    public void testMultiCombination() {
        Integer totalPrice = checkoutService.calculateTotal("AAAAA");
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(200)); // 5 A's for 200

        totalPrice = checkoutService.calculateTotal("BB");
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(45)); // 2 B's for 45


        totalPrice = checkoutService.calculateTotal("EEB");
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(80)); // 2 E's for 80, B is free

        totalPrice = checkoutService.calculateTotal("XYZ");
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(-1)); // Invalid SKUs, should return -1

        totalPrice = checkoutService.calculateTotal("FFF");
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(20)); // 3 F's, pay for 2 (10 each), get 1 free

        totalPrice = checkoutService.calculateTotal("FFFFFF");
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(40)); // 6 F's, pay for 4 (10 each), get 2 free

        totalPrice = checkoutService.calculateTotal("FF");
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(20)); // 2 F's, pay for 2 (10 each), no free item since we need 3 F's to get the discount

        totalPrice = checkoutService.calculateTotal("FFFF");
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(30)); // 4 F's, pay for 3 (10 each), get 1 free


    }




}

