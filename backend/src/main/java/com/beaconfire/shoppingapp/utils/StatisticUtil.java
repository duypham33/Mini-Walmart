package com.beaconfire.shoppingapp.utils;

import com.beaconfire.shoppingapp.dtos.statistics.SimpleStatisticDto;
import java.util.List;

public class StatisticUtil {
    public static List<SimpleStatisticDto> prepareUserStatistics(Long userId){
        List<SimpleStatisticDto> res = List.of(
                SimpleStatisticDto.builder()
                        .name("Subscription").procedureName("subscription")
                        .inputs(List.of(userId))
                        .build(),
                SimpleStatisticDto.builder()
                        .name("Total Savings").procedureName("savings")
                        .inputs(List.of(userId))
                        .build(),
                SimpleStatisticDto.builder()
                        .name("Total E-Cash").procedureName("ecash")
                        .inputs(List.of(userId))
                        .build(),
                SimpleStatisticDto.builder()
                        .name("Number of Orders").procedureName("ordersNum")
                        .inputs(List.of(userId))
                        .build(),
                SimpleStatisticDto.builder()
                        .name("Completed Orders").procedureName("completedOrdersNum")
                        .inputs(List.of(userId))
                        .build(),
                SimpleStatisticDto.builder()
                        .name("Canceled Orders").procedureName("canceledOrdersNum")
                        .inputs(List.of(userId))
                        .build(),
                SimpleStatisticDto.builder()
                        .name("Number of Order Templates").procedureName("templates")
                        .inputs(List.of(userId))
                        .build(),
                SimpleStatisticDto.builder()
                        .name("The Most Ordered Template").procedureName("frequentTemplate")
                        .inputs(List.of(userId))
                        .build(),
                SimpleStatisticDto.builder()
                        .name("Most Frequently Purchased Product").procedureName("frequentlyProducts")
                        .inputs(List.of(userId, 3L))
                        .build(),
                SimpleStatisticDto.builder()
                        .name("Most Recently Purchased Product").procedureName("recentlyProducts")
                        .inputs(List.of(userId, 3L))
                        .build()
        );

        return res;
    }

    public static List<SimpleStatisticDto> prepareSellerStatistics(){
        List<SimpleStatisticDto> res = List.of(
                SimpleStatisticDto.builder()
                        .name("Number of Users").procedureName("usersNum")
                        .inputs(List.of())
                        .build(),
                SimpleStatisticDto.builder()
                        .name("Ordered Users Rate").procedureName("orderedUsersRate")
                        .inputs(List.of())
                        .build(),
                SimpleStatisticDto.builder()
                        .name("Subscribed Users Rate").procedureName("subscribedUsersRate")
                        .inputs(List.of())
                        .build(),
                SimpleStatisticDto.builder()
                        .name("Number of Subscriptions").procedureName("subscriptionNums")
                        .inputs(List.of())
                        .build(),
                SimpleStatisticDto.builder()
                        .name("Subscription Profit").procedureName("subscriptionProfit")
                        .inputs(List.of())
                        .build(),
                SimpleStatisticDto.builder()
                        .name("Selling Profit").procedureName("sellingProfit")
                        .inputs(List.of())
                        .build(),
                SimpleStatisticDto.builder()
                        .name("Orders By Status").procedureName("ordersByStatus")
                        .inputs(List.of())
                        .build(),
                SimpleStatisticDto.builder()
                        .name("Number of Categories").procedureName("categoriesNum")
                        .inputs(List.of())
                        .build(),
                SimpleStatisticDto.builder()
                        .name("Number of Brands").procedureName("brandsNum")
                        .inputs(List.of())
                        .build(),
                SimpleStatisticDto.builder()
                        .name("Number of Products").procedureName("productsNum")
                        .inputs(List.of())
                        .build(),
                SimpleStatisticDto.builder()
                        .name("Number of Sold Products").procedureName("soldProducts")
                        .inputs(List.of())
                        .build(),
                SimpleStatisticDto.builder()
                        .name("Most Profitable Products").procedureName("topProfit")
                        .inputs(List.of(3L))
                        .build(),
                SimpleStatisticDto.builder()
                        .name("Most Popular Products").procedureName("topPopular")
                        .inputs(List.of(3L))
                        .build()
        );

        return res;
    }
}
