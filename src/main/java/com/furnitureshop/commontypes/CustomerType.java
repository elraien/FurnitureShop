package com.furnitureshop.commontypes;

/**
 * NEW_CUSTOMER - only productDiscount will be applied
 * SPECIAL_AGREEMENT - productDiscount, specialOffer will be applied
 * VIP - productDiscount, specialOffer and totalSum discounts
 */
public enum CustomerType {
    NEW_CUSTOMER,
    SPECIAL_AGREEMENT,
    VIP
}
