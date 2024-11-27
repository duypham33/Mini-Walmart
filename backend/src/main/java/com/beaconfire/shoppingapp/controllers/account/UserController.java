package com.beaconfire.shoppingapp.controllers.account;

import com.beaconfire.shoppingapp.dtos.ResponseDto;
import com.beaconfire.shoppingapp.dtos.account.feature.CartDto;
import com.beaconfire.shoppingapp.dtos.account.feature.CartItemDto;
import com.beaconfire.shoppingapp.dtos.account.feature.ProductReferralSendRequestDto;
import com.beaconfire.shoppingapp.dtos.account.feature.UpdateCartRequestDto;
import com.beaconfire.shoppingapp.dtos.account.feature.orderTemplate.CreateOrderTemplateRequestDto;
import com.beaconfire.shoppingapp.dtos.account.feature.orderTemplate.OrderTemplateDto;
import com.beaconfire.shoppingapp.dtos.account.feature.orderTemplate.OrderTemplateItemDto;
import com.beaconfire.shoppingapp.dtos.account.feature.orderTemplate.UpdateOrderTemplateRequestDto;
import com.beaconfire.shoppingapp.dtos.account.subscription.SubcribeRequestDto;
import com.beaconfire.shoppingapp.dtos.account.user.UserDto;
import com.beaconfire.shoppingapp.dtos.product.ProductDto;
import com.beaconfire.shoppingapp.entities.account.feature.orderTemplate.OrderTemplate;
import com.beaconfire.shoppingapp.entities.account.subscription.AccountSubscription;
import com.beaconfire.shoppingapp.services.account.SubscriptionService;
import com.beaconfire.shoppingapp.services.account.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping
public class UserController {
    private final UserService userService;
    private final SubscriptionService subscriptionService;

    public UserController(UserService userService, SubscriptionService subscriptionService){
        this.userService = userService;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/subscription-plans")
    public ResponseEntity<List<String>> viewSubscriptionPlans(){
        return ResponseEntity.ok(subscriptionService.viewPlans());
    }

    @PostMapping("/subscribe-plan")
    public ResponseEntity<ResponseDto<AccountSubscription>> subscribePlan(
            Authentication authentication, @RequestBody SubcribeRequestDto subcribeRequestDto
    ){
        var user = (UserDto) authentication.getDetails();
        return subscriptionService.subscribe(user.getId(), subcribeRequestDto.getPlanType()).toResponseEntity();
    }

    @GetMapping("/my-subscription")
    public ResponseEntity<ResponseDto<AccountSubscription>> viewSubscription(Authentication authentication){
        var user = (UserDto) authentication.getDetails();
        return ResponseEntity.ok(subscriptionService.getUserSubscription(user.getId()));
    }

    @PatchMapping("/cancel-subscription")
    public ResponseEntity<ResponseDto<AccountSubscription>> cancelSubscription(Authentication authentication){
        var user = (UserDto) authentication.getDetails();
        return subscriptionService.cancelUserSubscription(user.getId()).toResponseEntity();
    }


    @PatchMapping("/update-cart")
    public ResponseEntity<ResponseDto<CartItemDto>> updateCart(
            Authentication authentication, @RequestBody UpdateCartRequestDto requestDto
    ){
        var user = (UserDto) authentication.getDetails();
        return userService.updateCart(user.getId(), requestDto).toResponseEntity();
    }

    @GetMapping("/cart")
    public ResponseEntity<ResponseDto<CartDto>> viewMyCart(Authentication authentication){
        var user = (UserDto) authentication.getDetails();
        return ResponseEntity.ok(userService.viewMyCart(user.getId()));
    }

    @GetMapping("/eCash")
    public ResponseEntity<ResponseDto<Float>> getECash(Authentication authentication){
        var user = (UserDto) authentication.getDetails();
        return ResponseEntity.ok(userService.getECash(user.getId()));
    }

//    @PutMapping("/eCash")
//    public ResponseEntity<ResponseDto<Float>> updateECash(Authentication authentication, @RequestParam Float unit){
//        var user = (UserDto) authentication.getDetails();
//        return ResponseEntity.ok(userService.updateECash(user.getId(), unit));
//    }

    @PostMapping("/order-template")
    public ResponseEntity<ResponseDto<OrderTemplate>> createOrderTemplate(
            Authentication authentication, @RequestBody CreateOrderTemplateRequestDto requestDto
    ){
        var user = (UserDto) authentication.getDetails();
        return ResponseEntity.ok(userService.createOrderTemplate(user.getId(), requestDto));
    }

    @GetMapping("/order-template")
    public ResponseEntity<ResponseDto<List<OrderTemplate>>> viewOrderTemplates(
            Authentication authentication
    ){
        var user = (UserDto) authentication.getDetails();
        return userService.viewOrderTemplates(user.getId()).toResponseEntity();
    }

    @GetMapping("/order-template/{templateId}")
    public ResponseEntity<ResponseDto<OrderTemplateDto>> viewOrderTemplate(
            @PathVariable Long templateId
    ){
        return userService.viewOrderTemplate(templateId).toResponseEntity();
    }

    @PatchMapping("/order-template")
    public ResponseEntity<ResponseDto<OrderTemplateItemDto>> updateOrderTemplate(
            @RequestBody UpdateOrderTemplateRequestDto requestDto
    ){
        return userService.updateOrderTemplate(requestDto).toResponseEntity();
    }

    @GetMapping("/watchlist/products/all")
    public ResponseEntity<ResponseDto<List<ProductDto>>> viewWatchList(Authentication authentication){
        var user = (UserDto) authentication.getDetails();
        return userService.viewWatchList(user.getId(), user.isAdmin()).toResponseEntity();
    }

    @PostMapping("/watchlist/product/{watchListProductId}")
    public ResponseEntity<ResponseDto<ProductDto>> addProductToWatchList(
            Authentication authentication, @PathVariable Long watchListProductId
    ){
        var user = (UserDto) authentication.getDetails();
        return userService.addProductToWatchList(user.getId(), watchListProductId).toResponseEntity();
    }

    @DeleteMapping("/watchlist/product/{watchListProductId}")
    public ResponseEntity<ResponseDto<ProductDto>> removeProductFromWatchList(
            Authentication authentication, @PathVariable Long watchListProductId
    ){
        var user = (UserDto) authentication.getDetails();
        return userService.removeProductFromWatchList(user.getId(), watchListProductId).toResponseEntity();
    }

    @PostMapping("/referral")
    public ResponseEntity<ResponseDto<String>> sendProductReferral(
            Authentication authentication, @RequestBody ProductReferralSendRequestDto requestDto
    ){
        var user = (UserDto) authentication.getDetails();
        return ResponseEntity.ok(userService.sendProductReferral(user.getId(), requestDto));
    }
}
