package com.beaconfire.shoppingapp.dtos.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderPreviewDto {
    private EstimatedShippingDto shipping;
    private Float finalDueAmount;
    private String saving;
    private List<String> recordLog;
    private List<InvoiceItemDto> invoiceItems;
}
