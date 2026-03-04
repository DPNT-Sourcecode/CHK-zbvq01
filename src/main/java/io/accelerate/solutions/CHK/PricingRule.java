package io.accelerate.solutions.CHK;

public interface PricingRule {
    int getPriority();
    int apply(PricingContext context);
}
