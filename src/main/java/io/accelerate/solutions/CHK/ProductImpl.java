package io.accelerate.solutions.CHK;

public class ProductImpl implements Product {
    private String sku;
    private Integer unitPrice;
    private Integer discountedPrice;
    private Integer discountedMultiplier;

    public ProductImpl(String sku, Integer unitPrice, Integer discountedPrice, Integer discountedMultiplier) {
        this.sku = sku;
        this.unitPrice = unitPrice;
        this.discountedPrice = discountedPrice;
        this.discountedMultiplier = discountedMultiplier;
    }

    @Override
    public String getSku() {
        return sku;
    }

    @Override
    public Integer getUnitPrice() {
        return unitPrice;
    }

    @Override
    public Integer getDiscountedPrice() {
        return discountedPrice;
    }

    @Override
    public Integer getDiscountedMultiplier() {
        return discountedMultiplier;
    }
}
