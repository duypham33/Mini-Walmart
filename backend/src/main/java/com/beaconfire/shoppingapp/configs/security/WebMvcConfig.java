package com.beaconfire.shoppingapp.configs.security;

import com.beaconfire.shoppingapp.interceptors.OwnershipInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration @EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
//    private final OwnershipInterceptor ownershipInterceptor;
//
//    public WebMvcConfig(OwnershipInterceptor ownershipInterceptor){
//        this.ownershipInterceptor = ownershipInterceptor;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(ownershipInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/login", "/signup", "/logout", "/static/**");
//    }
}
