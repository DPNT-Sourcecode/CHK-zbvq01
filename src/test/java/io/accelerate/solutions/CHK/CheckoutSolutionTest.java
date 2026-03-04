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
        assertThat(chk.checkout("A"), equalTo(50));
    }

    @Test
    public void testAllProducts() {
        assertThat(chk.checkout(""), equalTo(0));
        assertThat(chk.checkout("Z"), equalTo(-1));
        assertThat(chk.checkout("ZA"), equalTo(49));
        assertThat(chk.checkout("A"), equalTo(50));
        assertThat(chk.checkout("AB"), equalTo(80));
        assertThat(chk.checkout("ABC"), equalTo(100));
        assertThat(chk.checkout("CDBA"), equalTo(115));
        assertThat(chk.checkout("AAABBBCCC"), equalTo(205));
    }
}

