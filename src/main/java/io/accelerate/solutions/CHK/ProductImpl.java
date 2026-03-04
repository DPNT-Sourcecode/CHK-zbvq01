package io.accelerate.solutions.CHK;

import lombok.Data;

@Data
public class ProductImpl implements Product {
    private String sku;
    private Integer unitPrice;

    public ProductImpl(String sku, Integer unitPrice) {
        this.sku = sku;
        this.unitPrice = unitPrice;
    }

    @Override
    public String getSku() {
        return sku;
    }

    @Override
    public Integer getUnitPrice() {
        return unitPrice;
    }

}

