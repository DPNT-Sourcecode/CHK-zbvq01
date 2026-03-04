package io.accelerate.solutions.CHK;

import io.accelerate.solutions.SUM.SumSolution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CheckoutSolutionTest {
    private CheckoutSolution chk;
    public static void main(String[] args) {
        CheckoutSolution solution = new CheckoutSolution();
        System.out.println(solution.checkout("")); // Expected output: 0
        System.out.println(solution.checkout("A")); // Expected output: 50
        System.out.println(solution.checkout("AB")); // Expected output: 80
        System.out.println(solution.checkout("CDBA")); // Expected output: 115
        System.out.println(solution.checkout("AAABBBCCC")); // Expected output: 370
    }

    @BeforeEach
    public void setUp() {
        chk = new CheckoutSolution();
    }

    @Test
    public void testSimpleProductA() {
        assertThat(chk., equalTo(2));
    }
}
