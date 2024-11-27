package com.beaconfire.shoppingapp.dtos.queryFeature;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class QueryPage {
    private Integer pageSize;
    private Integer page;
//    private Boolean hasNext;

    @Override
    public String toString(){
        int skips = getSkip();
        int limit = (pageSize > 0 ? pageSize : 1) + 1;  // plus 1 for checking hasNext
        return String.format(" limit %d offset %d ", limit, skips);
    }

    public Integer getSkip(){
        return pageSize * (page > 0 ? page - 1 : 0);
    }

    public Integer getPageSize() {
        return pageSize > 0 ? pageSize : 1;
    }
}