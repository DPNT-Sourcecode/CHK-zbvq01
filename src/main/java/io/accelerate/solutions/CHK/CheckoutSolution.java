package io.accelerate.solutions.CHK;

import io.accelerate.runner.SolutionNotImplementedException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CheckoutSolution {
    private final CheckoutController checkoutController = new CheckoutController();
    public Integer checkout(String skus) {
        //checks if have any input
        if (StringUtils.isEmpty(skus)) {
            return 0;
        } else {
            return  checkoutController.checkout(skus);
        }

    }
}
