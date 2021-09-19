package com.furnitureshop.model.cart;

import com.furnitureshop.model.products.Product;
import org.junit.Test;

import java.util.List;

import static com.furnitureshop.TestUtils.prepareCart;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartTest {

    Cart cart = new Cart();

    @Test
    public void testAddToCartById() {
        Cart emptyCart = prepareCart(0, 0, 0);

        // Perform
        Cart resultCart = cart.addToCartById(emptyCart, 1, 2);

        // Verify
        assertEquals(resultCart.getCart().get(0), new Product(1, "Table", 1000, 2));
    }

    @Test
    public void testRemoveFromCart() {
        Cart preparedCart = prepareCart(4, 1, 1);

        // Perform
        List<Product> resultCart = cart.removeFromCart(preparedCart, 1);

        // Verify
        assertEquals(2, resultCart.size());
    }

    @Test
    public void testUpdateQuantity() {
        Cart preparedCart = prepareCart(4, 1, 1);

        // Perform
        Cart result = cart.updateQuantity(preparedCart, 2, true, 2);

        // Verify
        assertEquals(3, result.getCart().get(1).getQuantity());
    }
}