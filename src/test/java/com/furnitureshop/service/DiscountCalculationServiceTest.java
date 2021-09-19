package com.furnitureshop.service;

import com.furnitureshop.commontypes.CustomerType;
import com.furnitureshop.commontypes.DiscountType;
import com.furnitureshop.model.cart.Cart;
import org.junit.Test;

import static com.furnitureshop.TestUtils.prepareCart;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountCalculationServiceTest {

    DiscountCalculationService calculationService = new DiscountCalculationService();

    @Test
    public void testCalculateTotalSumWithDiscount_product_discount() {
        Cart initialCart = prepareCart(1, 2, 3);

        // Perform
        Cart actualResult = calculationService.resolveDiscountTypeAndCalculateTotalSum(initialCart, CustomerType.NEW_CUSTOMER);

        // Verify
        assertEquals(actualResult.getCalculatedDiscount(), DiscountType.PRODUCT_DISCOUNT);
        assertEquals(actualResult.getTotalCartSum(), 7225.0D);
    }

    @Test
    public void testCalculateTotalSumWithDiscount_special_offer() {
        Cart initialCart = prepareCart(3, 3, 3);

        // Perform
        Cart actualResult = calculationService.resolveDiscountTypeAndCalculateTotalSum(initialCart, CustomerType.SPECIAL_AGREEMENT);

        // Verify
        assertEquals(actualResult.getCalculatedDiscount(), DiscountType.SPECIAL_OFFER);
        assertEquals(actualResult.getTotalCartSum(), 9000.0D);
    }

    @Test
    public void testCalculateTotalSumWithDiscount_total_sum_discount() {
        Cart initialCart = prepareCart(1, 1, 2);

        // Perform
        Cart actualResult = calculationService.resolveDiscountTypeAndCalculateTotalSum(initialCart, CustomerType.VIP);

        // Verify
        assertEquals(actualResult.getCalculatedDiscount(), DiscountType.TOTAL_SUM_DISCOUNT);
        assertEquals(actualResult.getTotalCartSum(), 4000.0D);
    }
}