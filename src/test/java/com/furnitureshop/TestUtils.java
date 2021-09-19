package com.furnitureshop;

import com.furnitureshop.model.cart.Cart;
import com.furnitureshop.model.products.Product;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {
    public static Cart prepareCart(int quantity1, int quantity2, int quantity3) {
        Product product1 = new Product(1, "Table", 1000, quantity1);
        Product product2 = new Product(2, "Sofa", 3000, quantity2);
        Product product3 = new Product(3, "Chair", 500, quantity3);

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        return new Cart(products, null, null);
    }
}
