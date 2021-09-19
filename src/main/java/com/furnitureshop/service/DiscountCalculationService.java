package com.furnitureshop.service;

import com.furnitureshop.commontypes.CustomerType;
import com.furnitureshop.commontypes.DiscountType;
import com.furnitureshop.model.cart.Cart;
import com.furnitureshop.model.products.Product;

/**
 * Service which compares 3 discounts and applies the one which gives most rebate
 */
public class DiscountCalculationService{

    public Cart resolveDiscountTypeAndCalculateTotalSum(Cart cart, CustomerType customer) {
        switch (customer) {
            case SPECIAL_AGREEMENT:
                return calculatePriceWithProductDiscountOrSpecialOffer(cart);
            case VIP:
                return calculatePriceWithBestDiscount(cart);
            default:
                cart.setCalculatedDiscount(DiscountType.PRODUCT_DISCOUNT);
                return calculateProductDiscount(cart);
        }
    }

    public Cart calculatePriceWithBestDiscount(Cart cart) {
        Double totalSumWithDiscount = calculateTotalSumWithTotalSumDiscount(cart);
        Double totalSum = calculatePriceWithProductDiscountOrSpecialOffer(cart).getTotalCartSum();

        if (totalSumWithDiscount < totalSum) {
            cart.setCalculatedDiscount(DiscountType.TOTAL_SUM_DISCOUNT);
            cart.setTotalCartSum(totalSumWithDiscount);
        } else {
            cart.setTotalCartSum(totalSum);
        }
        return cart;
    }

    public Cart calculatePriceWithProductDiscountOrSpecialOffer(Cart cart) {
        Double productPriceWithProductDiscount = calculateProductDiscount(cart).getTotalCartSum();
        Double productPriceSpecialOffer = calculateSpecialOffer(cart);
        Double updatedPrice;
        if (productPriceWithProductDiscount < productPriceSpecialOffer) {
            updatedPrice = productPriceWithProductDiscount;
            cart.setCalculatedDiscount(DiscountType.PRODUCT_DISCOUNT);
        } else {
            updatedPrice = productPriceSpecialOffer;
            cart.setCalculatedDiscount(DiscountType.SPECIAL_OFFER);
        }
        cart.setTotalCartSum(updatedPrice);
        return cart;
    }

    /**
     * @param cart
     * @return price with PRODUCT_DISCOUNT 15 %
     */
    public Cart calculateProductDiscount(Cart cart) {
        Double sum = 0D;
        for (Product product : cart.getCart()) {
            int quantity = product.getQuantity();
            Double newProductPrice = product.getPrice() * 0.85 * quantity;
            sum += newProductPrice;
        }
        cart.setTotalCartSum(sum);
        return cart;
    }

    /**
     * @param cart
     * @return SPECIAL_OFFER price (buy 3 items pay for 2)
     */
    public Double calculateSpecialOffer(Cart cart) {
        Double sum = 0d;
        for (Product product : cart.getCart()) {
            int quantity = product.getQuantity();
            if (quantity < 2) {
                sum += product.getPrice() * quantity;
            } else {
                int updatedQuantity = Math.round((quantity / 3 * 2) + quantity % 3);
                sum += updatedQuantity * product.getPrice();
            }
        }
        return sum;
    }

    /**
     * @param cart
     * @return price with TOTAL_SUM_DISCOUNT 20%
     */
    public Double calculateTotalSumWithTotalSumDiscount(Cart cart) {
        Double sum = 0D;
        for (Product product : cart.getCart()) {
            int quantity = product.getQuantity();
            double newProductPrice = product.getPrice() * 0.80 * quantity;
            sum += newProductPrice;
        }
        return sum;
    }
}
