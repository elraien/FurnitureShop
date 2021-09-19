package com.furnitureshop.ui;

import com.furnitureshop.commontypes.CustomerType;
import com.furnitureshop.model.cart.Cart;
import com.furnitureshop.model.products.Product;
import com.furnitureshop.model.products.ProductStock;

import java.util.List;
import java.util.Scanner;

public class UI {
    Cart cart = new Cart();
    private int choice = 0;
    CustomerType customerType;

    public UI() {
        showMenu();
    }

    public void initialScreen() {
        System.out.println("Please choose customer type: ");
        System.out.println("1. NEW CUSTOMER");
        System.out.println("2. SPECIAL AGREEMENT");
        System.out.println("3. VIP");
    }

    public void menuScreen() {
        System.out.println("1. Display Store Products");
        System.out.println("2. Display Cart");
        System.out.println("0. Exit");
    }

    public void cartMenu() {
        System.out.println("Choose 1) ACTION 2) PRODUCT ID and 3) QUANTITY:");
        System.out.println("1. Add to Cart");
        System.out.println("2. Remove From Cart");
        System.out.println("0. Exit");
    }

    public void showMenu() {
        initialScreen();
        getUserInput();
        customerType = innerChoice1();

        do {
            menuScreen();
            getUserInput();

            switch (choice) {
                case 0:
                    System.exit(0);
                case 1:
                    displayCase1(cart, customerType);
                    break;
                case 2:
                    showCart(cart, customerType);
                    break;
                default:
                    break;
            }
        } while (choice != 0);
    }

    private void displayCase1(Cart cart, CustomerType customerType) {
        displayAllProducts();
        cartMenu();
        getUserInput();
        innerChoice2(cart, customerType);
    }

    private CustomerType innerChoice1() {
        switch (choice) {
            case 2:
                return CustomerType.SPECIAL_AGREEMENT;
            case 3:
                return CustomerType.VIP;
            default:
                return CustomerType.NEW_CUSTOMER;
        }
    }

    private void innerChoice2(Cart cart, CustomerType customerType) {
        switch (choice) {
            case 1:
                addProductToCart(cart, customerType);
                break;
            case 2:
                updateCartItemQuantity(cart, customerType, false);
                break;
            case 3:
                updateCartItemQuantity(cart, customerType, true);
                break;
            default:
                break;
        }
    }

    private void addProductToCart(Cart cart, CustomerType customerType) {
        int id = getUserInput();
        int quantity = getUserInput();
        addProductToCart(cart, id, quantity);
        showCart(cart, customerType);
    }

    private void updateCartItemQuantity(Cart cart, CustomerType customerType, boolean increase) {
        int idForAddingToCart = getUserInput();
        int quantityForAdding = getUserInput();
        cart.updateQuantity(cart, idForAddingToCart, increase, quantityForAdding);
        showCart(cart, customerType);
    }

    private void displayAllProducts() {
        List<Product> products = new ProductStock().getProducts();
        for (Product product : products) {
            System.out.println(product.getId() + "- " + product.getName() + " " + product.getPrice());
        }
    }

    private void addProductToCart(Cart cart, int id, int quantity) {
        cart.addToCartById(cart, id, quantity);
    }

    private void showCart(Cart cart, CustomerType customerType) {
        cart.printCartContents(cart, customerType);
    }

    private int getUserInput() {
        Scanner input = new Scanner(System.in);
        choice = Integer.parseInt(input.nextLine());
        return choice;
    }
}
