package io.accelerate.solutions.CHK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CheckoutServiceTest {
    private CheckoutService checkoutService;

    @BeforeEach
    public void setUp() {
        checkoutService = new CheckoutService(new SKUParser(),);
    }


    @Test
    public void testCalculateTotal() {
        Product productA = new ProductImpl("A", 50, 130, 3);
        Product productB = new ProductImpl("B", 30, 45, 2);
        Product productC = new ProductImpl("C", 20, null, null);
        Product productD = new ProductImpl("D", 15, null, null);

        List<Product> products = Arrays.asList(productA, productB, productC, productD);
        Integer totalPrice = checkoutService.calculateTotal(products);
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(115));
    }

    @Test
    public void testSimpleProductA() {
        Product productA = new ProductImpl("A", 50, 130, 3);
        Product productA2 = new ProductImpl("A", 50, 130, 3);
        List<Product> products = Arrays.asList(productA, productA2);
        Integer totalPrice = checkoutService.calculateTotal(products);
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(100));
    }

    @Test
    public void testCombinationOfProducts() {
        Product productA = new ProductImpl("A", 50, 130, 3);
        Product productB = new ProductImpl("B", 30, 45, 2);
        Product productC = new ProductImpl("C", 20, null, null);
        List<Product> products = Arrays.asList(productA, productB, productC);
        Integer totalPrice = checkoutService.calculateTotal(products);
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(100));
    }


    @Test
    public void testMultipleEqualsProducts() {
        Product productA = new ProductImpl("A", 50, 130, 3);
        Product productA2 = new ProductImpl("A", 50, 130, 3);
        Product productA3 = new ProductImpl("A", 50, 130, 3);

        List<Product> products = Arrays.asList(productA, productA2, productA3);
        Integer totalPrice = checkoutService.calculateTotal(products);
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(130));
    }

    @Test
    public void testMultipleEqualsProductsPlus1() {
        Product productA = new ProductImpl("A", 50, 130, 3);
        Product productA2 = new ProductImpl("A", 50, 130, 3);
        Product productA3 = new ProductImpl("A", 50, 130, 3);
        Product productA4 = new ProductImpl("A", 50, 130, 3);

        List<Product> products = Arrays.asList(productA, productA2, productA3, productA4);
        Integer totalPrice = checkoutService.calculateTotal(products);
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(180));
    }

    @Test
    public void testTwoDifferentProductsWithDiscount() {
        Product productA = new ProductImpl("A", 50, 130, 3);
        Product productA2 = new ProductImpl("A", 50, 130, 3);
        Product productA3 = new ProductImpl("A", 50, 130, 3);
        Product productB = new ProductImpl("B", 30, 45, 2);
        Product productB2 = new ProductImpl("B", 30, 45, 2);

        List<Product> products = Arrays.asList(productA, productA2, productA3, productB, productB2);
        Integer totalPrice = checkoutService.calculateTotal(products);
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(175));
    }


    @Test
    public void testProducsWithNoDiscount() {
        Product productC = new ProductImpl("C", 20, null, null);
        Product productD = new ProductImpl("D", 15, null, null);

        List<Product> products = Arrays.asList(productC, productD);
        Integer totalPrice = checkoutService.calculateTotal(products);
        System.out.println("Total Price: " + totalPrice);
        assertThat(totalPrice, equalTo(35));
    }




}
