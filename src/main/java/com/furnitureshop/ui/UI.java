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
    }

    public void startApp() {
        System.out.println("**************************");
        System.out.println("Welcome to FurnitureShop!");
        System.out.println("**************************");
        showMenu();
    }

    public void initialScreen() {
        System.out.println("Please choose customer type id: ");
        System.out.println("1. NEW CUSTOMER");
        System.out.println("2. SPECIAL AGREEMENT");
        System.out.println("3. VIP");
    }

    public void menuScreen() {
        System.out.println("Choose next action:");
        System.out.println("0. Exit");
        System.out.println("1. Display Store Products");
        System.out.println("2. Display Cart");
    }

    public void cartMenu() {
        System.out.println("Choose you next action:");
        System.out.println("0. Exit");
        System.out.println("1. Add to Cart");
        System.out.println("2. Remove From Cart");
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
            case 1:
                return CustomerType.NEW_CUSTOMER;
            case 2:
                return CustomerType.SPECIAL_AGREEMENT;
            case 3:
                return CustomerType.VIP;
            default:
                System.out.println("You've entered invalid value. Please choose 1, 2 or 3.");
                showMenu();
                return null;
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
        System.out.println("Choose product id:");
        int id = getUserInput();
        System.out.println("Choose product quantity:");
        int quantity = getUserInput();
        addProductToCart(cart, id, quantity);
        showCart(cart, customerType);
    }

    private void updateCartItemQuantity(Cart cart, CustomerType customerType, boolean increase) {
        if (cart.getCart().isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Choose product id:");
            int idForAddingToCart = getUserInput();
            System.out.println("Choose product quantity:");
            int quantityForAdding = getUserInput();
            cart.updateQuantity(cart, idForAddingToCart, increase, quantityForAdding);
            showCart(cart, customerType);
        }
    }

    private void displayAllProducts() {
        System.out.println("There are the following products in the shop:");
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
