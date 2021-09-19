package com.furnitureshop.model.products;

import java.util.ArrayList;
import java.util.List;

public class ProductStock {
    private final List<Product> products = new ArrayList<>();

    public ProductStock() {
        this.fillInitialProducts();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void fillInitialProducts() {
        products.add(new Product(1, "Table", 1000, 0));
        products.add(new Product(2, "Sofa", 3000, 0));
        products.add(new Product(3, "Chair", 500, 0));
    }

    @Override
    public String toString() {
        return "ProductStock{" +
                "products=" + products +
                '}';
    }
}
