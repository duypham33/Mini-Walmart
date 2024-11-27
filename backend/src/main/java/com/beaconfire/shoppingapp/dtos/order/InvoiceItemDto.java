package com.beaconfire.shoppingapp.dtos.order;

import com.beaconfire.shoppingapp.entities.order.InvoiceItem;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class InvoiceItemDto {
    private String detail;
    private Float subtotal;

    public static InvoiceItemDto fromInvoiceItem(InvoiceItem invoiceItem){
        return InvoiceItemDto.builder()
                .detail(invoiceItem.getDetail())
                .subtotal(invoiceItem.getSubtotal())
                .build();
    }
}
