package com.beaconfire.shoppingapp.exceptions;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class NotEnoughInventoryException extends RuntimeException{
    private final Long productId;
    private final String productName;
    private final Integer inStockQuantity;
    private final Integer orderedQuantity;

    public NotEnoughInventoryException(
            Long productId, String productName, Integer inStockQuantity, Integer orderedQuantity
    ){
//        String errorMessage = String.format("The stock only has %d '%s' left!", inStockQuantity, productName);
        super(String.format("The stock only has %d '%s' left!", inStockQuantity, productName));
        this.productId = productId;
        this.productName = productName;
        this.inStockQuantity = inStockQuantity;
        this.orderedQuantity = orderedQuantity;
    }
}
