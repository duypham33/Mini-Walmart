package com.beaconfire.shoppingapp.entities.account.feature.orderTemplate;

import java.io.Serializable;
import java.util.Objects;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TemplateToOrderId implements Serializable {
    private Long orderId;
    private Long templateId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateToOrderId that = (TemplateToOrderId) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(templateId, that.templateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, templateId);
    }
}
