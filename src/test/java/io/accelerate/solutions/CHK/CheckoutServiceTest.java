package io.accelerate.solutions.CHK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
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

        PricingRuleAggregator pricingRuleAggregator = new PricingRuleAggregator(catalog);
        checkoutService = new CheckoutService(new SKUParser(), catalog, pricingRuleAggregator.getPricingRules());
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
        assertThat(totalPrice, equalTo(150)); // Invalid SKUs, should return -1

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

        totalPrice = checkoutService.calculateTotal("VV");
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(90)); // 2 V for 90


        totalPrice = checkoutService.calculateTotal("VVV");
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(90)); // 3 V for 130

        totalPrice = checkoutService.calculateTotal("VVVV");
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(180)); // 4 V for 180

    }




}


