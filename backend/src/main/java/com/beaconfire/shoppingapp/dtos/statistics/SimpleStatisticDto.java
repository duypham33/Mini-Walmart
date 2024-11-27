package com.beaconfire.shoppingapp.dtos.statistics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleStatisticDto {
    private String name;
    private String description;
    @JsonIgnore
    private String procedureName;
    @JsonIgnore
    private List<Long> inputs;
    private String result;
    private List<String> results;
}
