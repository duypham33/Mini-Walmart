package com.beaconfire.shoppingapp.dtos.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class StatisticInput<T> {
    private Class<T> clazz;
    private T value;
}
