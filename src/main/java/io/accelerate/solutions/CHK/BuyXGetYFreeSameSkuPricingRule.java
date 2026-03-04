package io.accelerate.solutions.CHK;

public class BuyXGetYFreeSameSkuPricingRule implements PricingRule {
    private final String sku;
    private final int buyQty;
    private final int freeQty;
    private final int priority;

    public BuyXGetYFreeSameSkuPricingRule(String sku, int buyQty, int freeQty, int priority) {
        this.sku = sku;
        this.buyQty = buyQty;
        this.freeQty = freeQty;
        this.priority = priority;
    }

    @Override
    public int apply(Map<String, Integer> cart, Map<String, Product> catalog) {
        if (!cart.containsKey(sku)) {
            return 0; // No items of this SKU in the cart
        }

        int totalQty = cart.get(sku);
        int applicableSets = totalQty / (buyQty + freeQty);
        int remainingItems = totalQty % (buyQty + freeQty);

        // Calculate the price for the applicable sets
        int priceForSets = applicableSets * buyQty * catalog.get(sku).getPrice();

        // Calculate the price for the remaining items
        int priceForRemainingItems = Math.min(remainingItems, buyQty) * catalog.get(sku).getPrice();

        return priceForSets + priceForRemainingItems;
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
