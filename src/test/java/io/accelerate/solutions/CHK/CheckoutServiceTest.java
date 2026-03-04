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
        checkoutService = new CheckoutService();
    }


    @Test
    public void testCalculateTotal() {
        Product productA = new ProductImpl("A", 50, 130, 3);
        Product productB = new ProductImpl("B", 30, 45, 2);
        Product productC = new ProductImpl("C", 20, null, null);
        Product productD = new ProductImpl("D", 15, null, null);

        List<Product> products = Arrays.asList(productA, productA, productA, productB, productB, productC, productD);
        Integer totalPrice = checkoutService.calculateTotal(products);
        System.out.println("Total Price: " + totalPrice); // Expected output: Total Price: 195
    }

    @Test
    public void testSimpleProductA() {
        Product productA = new ProductImpl("A", 50, 130, 3);
        Product productA2 = new ProductImpl("A", 50, 130, 3);
        List<Product> products = Arrays.asList(productA, productA2);
        Integer totalPrice = checkoutService.calculateTotal(products);
        System.out.println("Total Price: " + totalPrice); // Expected output: Total Price: 100
        assertThat(totalPrice, equalTo(100));
    }

    @Test
    public void testCombinationOfProducts() {
        Product productA = new ProductImpl("A", 50, 130, 3);
        Product productB = new ProductImpl("B", 30, 45, 2);
        Product productC = new ProductImpl("C", 20, null, null);
        List<Product> products = Arrays.asList(productA, productB, productC);
        Integer totalPrice = checkoutService.calculateTotal(products);
        System.out.println("Total Price: " + totalPrice); // Expected output: Total Price: 100
        assertThat(totalPrice, equalTo(100));
    }


    @Test
    public void testMultipleEqualsProducts() {
        Product productA = new ProductImpl("A", 50, 130, 3);
        Product productA2 = new ProductImpl("A", 50, 130, 3);
        Product productA3 = new ProductImpl("A", 50, 130, 3);

        List<Product> products = Arrays.asList(productA, productA2, productA3);
        Integer totalPrice = checkoutService.calculateTotal(products);
        System.out.println("Total Price: " + totalPrice); // Expected output: Total Price: 130
        assertThat(totalPrice, equalTo(130));
    }

}



