package io.accelerate.solutions.CHK;

import io.accelerate.runner.SolutionNotImplementedException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CheckoutSolution {
    public Integer checkout(String skus) {
        //checks if have any input
        if (StringUtils.isEmpty(skus)) {
            return -1;
        } else {
            //create a new instance for each product depending on the input
            //create a list of products based on the list of chars in the input string
            String[] skusArray = skus.split("");
            List<Product> products = new ArrayList<>();
            for (String sku : skusArray) {
                switch (sku) {
                    case "A":
                        products.add(new ProductImpl("A", 50, 130, 3));
                        break;
                    case "B":
                        products.add(new ProductImpl("B", 30, 45, 2));
                        break;
                    case "C":
                        products.add(new ProductImpl("C", 20, null, null));
                        break;
                    case "D":
                        products.add(new ProductImpl("D", 15, null, null));
                        break;
                    default:
                        //for products that are not in the list, return value -1
                       return -1;
                }
            }

            //calculate the total price of the products in the list
            return new CheckoutService().calculateTotal(products);
        }

    }
}


