package com.furnitureshop.model.cart;

import com.furnitureshop.commontypes.CustomerType;
import com.furnitureshop.commontypes.DiscountType;
import com.furnitureshop.model.products.Product;
import com.furnitureshop.model.products.ProductStock;
import com.furnitureshop.service.DiscountCalculationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {
    DiscountCalculationService calculationService = new DiscountCalculationService();

    private List<Product> cart = new ArrayList<>();
    private DiscountType calculatedDiscount;
    private Double totalCartSum;

    public Cart(List<Product> cart, DiscountType calculatedDiscount, Double totalCartSum) {
        this.cart = cart;
        this.calculatedDiscount = calculatedDiscount;
        this.totalCartSum = totalCartSum;
    }

    public Cart() {
    }

    /* Getters, Setters */

    public List<Product> getCart() {
        return cart;
    }

    public void setCart(List<Product> cart) {
        this.cart = cart;
    }

    public DiscountType getCalculatedDiscount() {
        return calculatedDiscount;
    }

    public void setCalculatedDiscount(DiscountType calculatedDiscount) {
        this.calculatedDiscount = calculatedDiscount;
    }

    public void setTotalCartSum(Double totalCartSum) {
        this.totalCartSum = totalCartSum;
    }

    public Double getTotalCartSum() {
        return totalCartSum;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "calculationService=" + calculationService +
                ", cart=" + cart +
                ", calculatedDiscount=" + calculatedDiscount +
                ", totalCartSum=" + totalCartSum +
                '}';
    }

    public Cart addToCartById(Cart cart, int id, int quantity) {
        Product product;
        if (cart.getCart().isEmpty() || getProductFromCartById(cart, id) == null) {
            product = getProductFromStockById(id);
            product.setQuantity(quantity);
            addToCart(product);
        } else {
            product = getProductFromCartById(cart, id);
            var updatedQuantity = product.getQuantity() + quantity;
            product.setQuantity(updatedQuantity);
        }
        return cart;
    }

    public List<Product> removeFromCart(Cart cart, int productId) {
        if (cart.getCart().isEmpty()) {
            System.out.println("Your cart is empty.");
            return null;
        } else {
            Product selectedProduct = getProductFromCartById(cart, productId);
            System.out.println("cart " + cart);
            List<Product> products = cart.getCart();
            products.remove(selectedProduct);
            System.out.println("cart " + cart);

            return products;
        }
    }

    public Cart updateQuantity(Cart cart, int productId, boolean increase, int quantity) {
        Product selectedProduct = getProductFromCartById(cart, productId);
        System.out.println("selectedProduct " + selectedProduct);

        List<Product> updatedProducts;
        int increasedQuantity = selectedProduct.getQuantity() + quantity;
        int decreasedQuantity = selectedProduct.getQuantity() - quantity;

        if (increase) {
            updatedProducts = updateProductsNumber(cart, productId, increasedQuantity);
        } else if (decreasedQuantity > 0) {
            updatedProducts = updateProductsNumber(cart, productId, decreasedQuantity);
        } else {
            updatedProducts = removeFromCart(cart, productId);
        }

        cart.setCart(updatedProducts);
        return cart;
    }

    public void printCartContents(Cart cart, CustomerType customerType) {
        boolean cartIsEmpty = cart.getCart().isEmpty();

        if (cartIsEmpty) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("YOU HAVE THE FOLLOWING PRODUCTS IN YOUR CART: ");
            for (Product product : cart.getCart()) {
                System.out.println("PRODUCT NAME: " + product.getName() +
                        " ** QUANTITY: " + product.getQuantity() +
                        " ** RETAIL PRICE: " + product.getPrice());
            }

            calculateTotalWithDiscount(cart, customerType);
        }
    }

    private void addToCart(Product product) {
        cart.add(product);
    }

    private Product getProductFromStockById(int productId) {
        Product selectedProduct = null;
        List<Product> products = new ProductStock().getProducts();
        for (Product product : products) {
            if (Objects.equals(productId, product.getId())) {
                selectedProduct = product;
            }
        }
        return selectedProduct;
    }

    private Product getProductFromCartById(Cart cart, int productId) {
        Product selectedProduct = null;
        for (Product product : cart.getCart()) {
            if (Objects.equals(productId, product.getId())) {
                selectedProduct = product;
            }
        }
        return selectedProduct;
    }

    private List<Product> updateProductsNumber(Cart cart, int productId, int updatedQuantity) {
        Product selectedProduct = getProductFromCartById(cart, productId);
        selectedProduct.setQuantity(updatedQuantity);
        List<Product> products = cart.getCart();
        int index = products.indexOf(selectedProduct);
        products.set(index, selectedProduct);
        System.out.println("products " + products);
        return products;
    }

    private void calculateTotalWithDiscount(Cart cart, CustomerType customerType) {
        var result = calculationService.resolveDiscountTypeAndCalculateTotalSum(cart, customerType);

        System.out.println("Total sum with discount: " + result.getTotalCartSum() + " kr");
        System.out.println("Discount: " + result.getCalculatedDiscount());
    }
}
