package com.beaconfire.shoppingapp.services.account;

import com.beaconfire.shoppingapp.daos.account.UserDao;
import com.beaconfire.shoppingapp.daos.account.UserFeatureDao;
import com.beaconfire.shoppingapp.daos.account.WatchListDao;
import com.beaconfire.shoppingapp.dtos.ResponseDto;
import com.beaconfire.shoppingapp.dtos.account.auth.LoginRequestDto;
import com.beaconfire.shoppingapp.dtos.account.auth.LoginResponseDto;
import com.beaconfire.shoppingapp.dtos.account.auth.RegisterRequestDto;
import com.beaconfire.shoppingapp.dtos.account.feature.CartDto;
import com.beaconfire.shoppingapp.dtos.account.feature.CartItemDto;
import com.beaconfire.shoppingapp.dtos.account.feature.ProductReferralSendRequestDto;
import com.beaconfire.shoppingapp.dtos.account.feature.UpdateCartRequestDto;
import com.beaconfire.shoppingapp.dtos.account.feature.orderTemplate.CreateOrderTemplateRequestDto;
import com.beaconfire.shoppingapp.dtos.account.feature.orderTemplate.OrderTemplateDto;
import com.beaconfire.shoppingapp.dtos.account.feature.orderTemplate.OrderTemplateItemDto;
import com.beaconfire.shoppingapp.dtos.account.feature.orderTemplate.UpdateOrderTemplateRequestDto;
import com.beaconfire.shoppingapp.dtos.account.user.UserDto;
import com.beaconfire.shoppingapp.dtos.product.ProductDto;
import com.beaconfire.shoppingapp.entities.account.feature.CartItem;
import com.beaconfire.shoppingapp.entities.account.feature.ECash;
import com.beaconfire.shoppingapp.entities.account.feature.ProductReferral;
import com.beaconfire.shoppingapp.entities.account.feature.TotalSavings;
import com.beaconfire.shoppingapp.entities.account.feature.orderTemplate.OrderTemplate;
import com.beaconfire.shoppingapp.entities.account.feature.orderTemplate.OrderTemplateItem;
import com.beaconfire.shoppingapp.entities.account.user.User;
import com.beaconfire.shoppingapp.entities.account.watchlist.WatchList;
import com.beaconfire.shoppingapp.exceptions.InvalidCredentialsException;
import com.beaconfire.shoppingapp.security.AuthUserDetail;
import com.beaconfire.shoppingapp.security.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;
    private final UserFeatureDao featureDao;
    private final WatchListDao watchListDao;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserDao userDao, UserFeatureDao featureDao,
                       WatchListDao watchListDao, JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder){
        this.userDao = userDao;
        this.featureDao = featureDao;
        this.watchListDao = watchListDao;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public User findById(Long id){
        return userDao.getById(id);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto){
        String err = null;
        UserDto userDto = null;
        User user = userDao.getByUsername(loginRequestDto.getUsername());
        userDto = UserDto.fromUser(user);
        if(user == null)
            err = String.format("Username %s has not been registered yet!", loginRequestDto.getUsername());
        else if(!passwordEncoder.matches(loginRequestDto.getPassword(), user.getHashPassword()))
            throw new InvalidCredentialsException(user.getId(), loginRequestDto.getUsername(), loginRequestDto.getPassword());

        if(err == null)
            userDto.setToken(jwtTokenUtil.createToken(userDto));

        return LoginResponseDto.builder()
                .isSuccess(err == null)
                .message(err).user(err == null ? userDto : null)
                .build();
    }

    public AuthUserDetail getSubject(String token){
        return jwtTokenUtil.getSubject(token);
    }

    public ResponseDto<UserDto> register(RegisterRequestDto requestDto){
        if(userDao.getByUsername(requestDto.getUsername()) != null)
            return ResponseDto.get(null, String.format("Username %s has been registered already!", requestDto.getUsername()), HttpStatus.BAD_REQUEST);
        else if(userDao.getByEmail(requestDto.getEmail()) != null)
            return ResponseDto.get(null, String.format("Email %s has been registered already!", requestDto.getEmail()), HttpStatus.BAD_REQUEST);
        User user = userDao.createUser(requestDto.getUsername(), requestDto.getEmail(),
                passwordEncoder.encode(requestDto.getPassword()));

        return ResponseDto.get(UserDto.fromUser(user), "Registered successfully!");
    }


    public ResponseDto<CartItemDto> updateCart(long userId, UpdateCartRequestDto requestDto){
        CartItem item = featureDao.findCartItem(userId, requestDto.getProductId());
        // Remove case
        if(requestDto.getQuantity() < 1){
            if(item == null)
                return ResponseDto.get(null, "No action since You haven't added this product item into your shopping cart!");
            featureDao.deleteCartItem(item.getId());
            return ResponseDto.get(CartItemDto.fromCartItem(item), "The following product item was removed from your shopping cart!");
        }

        // Create new one
        if(item == null){
            item = featureDao.createCartItem(userId, requestDto.getProductId(), requestDto.getQuantity());
            return ResponseDto.get(CartItemDto.fromCartItem(item), "New product item was added into your shopping cart!");
        }

        // Update case
        featureDao.updateCartItem(item.getId(), requestDto.getQuantity());
        item.setQuantity(requestDto.getQuantity());
        return ResponseDto.get(CartItemDto.fromCartItem(item), "Updated the existing product item in your shopping cart!");
    }

    public ResponseDto<CartDto> viewMyCart(long userId){
        List<CartItemDto> items = featureDao.findCartItems(userId).stream().map(CartItemDto::fromCartItem).toList();
        String message = !items.isEmpty() ? null : "You have no item in your shopping cart!";
        return ResponseDto.get(CartDto.fromItemDtos(items), message);
    }

    public List<CartItem> getCartItems(long userId){
        return featureDao.findCartItems(userId);
    }

    public void clearCart(Long userId){
        featureDao.clearCart(userId);
    }

    public ResponseDto<Float> getECash(long userId){
        User user = userDao.getById(userId);
        ECash cash = user.getECash();
        Float amount = cash == null ? 0f : cash.getAmount();
        return ResponseDto.get(amount);
    }

    public ResponseDto<Float> updateECash(long userId, float unit){
        User user = userDao.getById(userId);
        ECash cash = user.getECash();
        Float amount = cash == null ? 0f : cash.getAmount();
        if(amount + unit < 0f){
            return ResponseDto.get(amount, "Your total cash is only $" + amount + " so you cannot perform this action", HttpStatus.NOT_ACCEPTABLE);
        }

        if(cash == null)
            featureDao.createECash(userId, amount + unit);
        else
            featureDao.updateECash(userId, amount + unit);

        String action = unit >= 0 ? "increased" : "decreased";
        return ResponseDto.get(amount + unit, String.format("Your total cash %s from $%f to $%f", action, amount, amount + unit));
    }

    public ResponseDto<Float> getTotalSavings(long userId){
        User user = userDao.getById(userId);
        TotalSavings savings = user.getTotalSavings();
        Float amount = savings == null ? 0f : savings.getAmount();
        return ResponseDto.get(amount);
    }

    public ResponseDto<Float> updateTotalSavings(long userId, float unit){
        User user = userDao.getById(userId);
        TotalSavings savings = user.getTotalSavings();
        Float amount = savings == null ? 0f : savings.getAmount();
        if(amount + unit < 0f){
            return ResponseDto.get(amount, "Your total savings is only $" + amount + " so you cannot perform this action", HttpStatus.NOT_ACCEPTABLE);
        }

        if(savings == null)
            featureDao.createSavings(userId, amount + unit);
        else
            featureDao.updateTotalSavings(userId, amount + unit);

        String action = unit >= 0 ? "increased" : "decreased";
        return ResponseDto.get(amount + unit, String.format("Your total savings %s from $%f to $%f", action, amount, amount + unit));
    }

    public ResponseDto<OrderTemplate> createOrderTemplate(long userId, CreateOrderTemplateRequestDto requestDto){
        return ResponseDto.get(featureDao.createOrderTemplate(userId, requestDto.getName()));
    }

    public ResponseDto<OrderTemplateItemDto> updateOrderTemplate(UpdateOrderTemplateRequestDto requestDto){
        OrderTemplate template = featureDao.findOrderTemplateById(requestDto.getTemplateId());
        if(template == null)
            return ResponseDto.get(null, "Template not found", HttpStatus.NOT_FOUND);

        if(requestDto.getProductId() == null)
            return ResponseDto.get(null, "The product item is required", HttpStatus.BAD_REQUEST);

        OrderTemplateItem item = featureDao.findOrderTemplateItem(requestDto.getTemplateId(), requestDto.getProductId());
        // Remove case
        if(requestDto.getQuantity() < 1){
            if(item == null)
                return ResponseDto.get(null, "No action since You haven't added this product item into this template!");
            featureDao.deleteOrderTemplateItem(item.getId());
            return ResponseDto.get(OrderTemplateItemDto.fromOrderTemplateItem(item), "The following item was removed from your order template!");
        }

        if(requestDto.getProductId().equals(requestDto.getSubstitutionPreferenceId()))
            return ResponseDto.get(null, "The substitution preference must be different from the chosen product!", HttpStatus.BAD_REQUEST);

        // Create new one
        if(item == null){
            item = featureDao.createOrderTemplateItem(
                    requestDto.getTemplateId(), requestDto.getProductId(), requestDto.getQuantity(), requestDto.getSubstitutionPreferenceId()
            );
            return ResponseDto.get(OrderTemplateItemDto.fromOrderTemplateItem(item), "New item was added into your order template!");
        }

        // Update case
        item = featureDao.updateOrderTemplateItem(item.getId(), requestDto.getQuantity(), requestDto.getSubstitutionPreferenceId());
        return ResponseDto.get(OrderTemplateItemDto.fromOrderTemplateItem(item), "Updated the existing item in your order template!");
    }

    public ResponseDto<OrderTemplateDto> viewOrderTemplate(long templateId){
        OrderTemplate template = featureDao.findOrderTemplateById(templateId);
        if(template == null){
            return ResponseDto.get(null, "Template not found", HttpStatus.NOT_FOUND);
        }

        return ResponseDto.get(OrderTemplateDto.fromOrderTemplate(template));
    }

    public ResponseDto<List<OrderTemplate>> viewOrderTemplates(long userId){
        return ResponseDto.get(featureDao.findOrderTemplates(userId));
    }

    public void createTemplateToOrder(Long orderId, Long templateId){
        featureDao.createTemplateToOrder(orderId, templateId);
    }

    public ResponseDto<List<ProductDto>> viewWatchList(Long userId, Boolean isAdmin){
        List<ProductDto> list = watchListDao.getWatchListForUser(userId).stream()
                .map(isAdmin ? ProductDto::fullInfo : ProductDto::fromProduct).toList();
        return ResponseDto.get(list, list.isEmpty() ? "Your watch list is empty!" : null);
    }

    public ResponseDto<ProductDto>  addProductToWatchList(Long userId, Long productId){
        WatchList watch = watchListDao.findWatchListByIds(userId, productId);
        if(watch != null) return ResponseDto.get(null, "The Product was previously added into the list!", HttpStatus.BAD_REQUEST);
        if(!watchListDao.addProduct(userId, productId)) return ResponseDto.get(null, "The Product NOT FOUND!", HttpStatus.NOT_FOUND);
        return ResponseDto.get(ProductDto.fromProduct(watchListDao.getWatchProduct(productId)), "Successfully added the product into your watch list!");
    }

    public ResponseDto<ProductDto>  removeProductFromWatchList(Long userId, Long productId){
        WatchList watch = watchListDao.findWatchListByIds(userId, productId);
        if(watch == null) return ResponseDto.get(null, "The Product is not found in the list!", HttpStatus.NOT_FOUND);
        watchListDao.removeProduct(userId, productId);
        return ResponseDto.get(ProductDto.fromProduct(watchListDao.getWatchProduct(productId)), "Successfully removed the product from your watch list!");
    }

    public ProductReferral findReferralByProduct(Long referredId, Long productId){
        return featureDao.findReferralByProduct(referredId, productId);
    }

    public ResponseDto<String> sendProductReferral(Long userId, ProductReferralSendRequestDto requestDto){
        var referral = featureDao.findReferralByProduct(requestDto.getReferredId(), requestDto.getProductId());
        if(referral != null)
            return ResponseDto.get(null, "This user already had this product referral! Please, try another product!", HttpStatus.BAD_REQUEST);
        if(!featureDao.createProductReferral(userId, requestDto.getReferredId(), requestDto.getProductId()))
            return ResponseDto.get(null, "Not found either product or user!", HttpStatus.BAD_REQUEST);
        String username = findById(requestDto.getReferredId()).getUsername();
        return ResponseDto.get("Sent referral to " + username + " successfully! You will get 5% of this product's subtotal when " + username + " first buy it!");
    }

    public void updateProductReferral(Long referredId, Long productId, Float rewardCash){
        featureDao.updateProductReferral(referredId, productId, rewardCash);
    }
}
