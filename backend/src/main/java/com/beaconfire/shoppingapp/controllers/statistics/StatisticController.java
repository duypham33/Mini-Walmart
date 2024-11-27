package com.beaconfire.shoppingapp.controllers.statistics;

import com.beaconfire.shoppingapp.dtos.ResponseDto;
import com.beaconfire.shoppingapp.dtos.account.user.UserDto;
import com.beaconfire.shoppingapp.dtos.statistics.AdminStatisticDto;
import com.beaconfire.shoppingapp.dtos.statistics.SimpleStatisticDto;
import com.beaconfire.shoppingapp.dtos.statistics.UserStatisticDto;
import com.beaconfire.shoppingapp.services.statistics.StatisticService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController @RequestMapping("")
public class StatisticController {
    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService){
        this.statisticService = statisticService;
    }

//    @GetMapping("/statistics")
//    public ResponseEntity<ResponseDto<List<SimpleStatisticDto>>> getAllUserStatistics(
//            Authentication authentication
//    ){
//        var user = (UserDto) authentication.getDetails();
//        return statisticService.getAllUserStatistics(user.getId()).toResponseEntity();
//    }

    @GetMapping("/statistics")
    public ResponseEntity<ResponseDto<UserStatisticDto>> getAllUserStatistics(
            Authentication authentication
    ){
        var user = (UserDto) authentication.getDetails();
        return statisticService.getAllUserStatistics2(user.getId()).toResponseEntity();
    }

    @GetMapping("/products/frequent/{limit}")
    public ResponseEntity<ResponseDto<List<String>>> mostFrequentlyPurchasedProducts(
            Authentication authentication, @PathVariable Integer limit
    ){
        var user = (UserDto) authentication.getDetails();
        return statisticService.mostFrequentlyPurchasedProducts(user.getId(), limit).toResponseEntity();
    }

    @GetMapping("/products/recent/{limit}")
    public ResponseEntity<ResponseDto<List<String>>> mostRecentlyPurchasedProducts(
            Authentication authentication, @PathVariable Integer limit
    ){
        var user = (UserDto) authentication.getDetails();
        return statisticService.mostRecentlyPurchasedProducts(user.getId(), limit).toResponseEntity();
    }


//    @GetMapping("/admin/statistics")
//    public ResponseEntity<ResponseDto<List<SimpleStatisticDto>>> getAllSellerStatistics(){
//        return statisticService.getAllSellerStatistics().toResponseEntity();
//    }

    @GetMapping("/admin/statistics")
    public ResponseEntity<ResponseDto<AdminStatisticDto>> getAllSellerStatistics(){
        return statisticService.getAllSellerStatistics2().toResponseEntity();
    }

    @GetMapping("/admin/products/profit/{limit}")
    public ResponseEntity<ResponseDto<List<String>>> mostProfitableProduct(
            @PathVariable Integer limit
    ){
        return statisticService.mostProfitableProduct(limit).toResponseEntity();
    }

    @GetMapping("/admin/products/popular/{limit}")
    public ResponseEntity<ResponseDto<List<String>>> mostPopularProduct(
            @PathVariable Integer limit
    ){
        return statisticService.mostPopularProduct(limit).toResponseEntity();
    }
}
