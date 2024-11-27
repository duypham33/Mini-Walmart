package com.beaconfire.shoppingapp.controllers.account;

import com.beaconfire.shoppingapp.dtos.ResponseDto;
import com.beaconfire.shoppingapp.dtos.account.auth.LoginRequestDto;
import com.beaconfire.shoppingapp.dtos.account.auth.RegisterRequestDto;
import com.beaconfire.shoppingapp.dtos.account.user.UserDto;
import com.beaconfire.shoppingapp.services.account.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController @RequestMapping("")
public class AuthController {
    private final UserService userService;
    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<UserDto>> login(@Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request){
        var res = userService.login(loginRequestDto);
        if(res.getIsSuccess()) return ResponseDto.get(res.getUser(), res.getMessage()).toResponseEntity();
        return ResponseDto.get(res.getUser(), res.getMessage(), HttpStatus.BAD_REQUEST).toResponseEntity();
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<UserDto>> register(@Valid @RequestBody RegisterRequestDto requestDto){
        return userService.register(requestDto).toResponseEntity();
    }

//    @GetMapping("/logout")
//    public ResponseEntity<ResponseDto<UserDto>> logout(HttpServletRequest request) {
//        HttpSession oldSession = request.getSession(false);
//        if(oldSession != null && oldSession.getAttribute("user") != null){
//            var user = (UserDto) oldSession.getAttribute("user");
//            // invalidate old session if it exists
//            oldSession.invalidate();
//            return ResponseEntity.ok(ResponseDto.get(user, "You logged out successfully!"));
//        }
//
//        return ResponseEntity.ok(ResponseDto.get(null, "You haven't logged in yet!", HttpStatus.BAD_REQUEST));
//    }

    @GetMapping("/checkAuth")
    public ResponseEntity<ResponseDto<UserDto>> checkAuth(Authentication authentication){
        var user = (UserDto) authentication.getDetails();
        return ResponseDto.get(user).toResponseEntity();
    }
}
