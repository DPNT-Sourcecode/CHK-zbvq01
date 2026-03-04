package io.accelerate.solutions.CHK;

//interface for all pricing rules that will be applied to the checkout process
// each rule will have a priority, so we can apply them in the correct order
// the apply method will take the context of the checkout process,
// which contains the current state of the cart and the catalog, and return the total price after applying the rule
public interface PricingRule {
    int getPriority();
    int apply(PricingContext context);
}

