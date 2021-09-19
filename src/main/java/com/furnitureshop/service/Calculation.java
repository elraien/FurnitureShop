package com.furnitureshop.service;

import com.furnitureshop.commontypes.CustomerType;
import com.furnitureshop.model.cart.Cart;

public interface Calculation {
    Cart calculateProductDiscount(Cart cart);

    Double calculateSpecialOffer(Cart cart);

    Double calculateTotalSumWithTotalSumDiscount(Cart cart);

    Cart calculatePriceWithBestDiscount(Cart cart);

    Cart resolveDiscountTypeAndCalculateTotalSum(Cart cart, CustomerType customer);

    Cart calculatePriceWithProductDiscountOrSpecialOffer(Cart cart);
}
