package com.beaconfire.shoppingapp.aspects;

import com.beaconfire.shoppingapp.dtos.ResponseDto;
import com.beaconfire.shoppingapp.dtos.account.user.UserDto;
import com.beaconfire.shoppingapp.dtos.order.OrderDetailDto;
import com.beaconfire.shoppingapp.exceptions.NotHaveAccessException;
import com.beaconfire.shoppingapp.services.account.OwnershipService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Aspect @Component
public class OwnershipCheckAspect {
    private final Logger logger = LoggerFactory.getLogger(OwnershipCheckAspect.class);

    private OwnershipService ownershipService;

    @Autowired
    private void setOwnershipService(OwnershipService ownershipService){
        this.ownershipService = ownershipService;
    }

    @Around("execution(* com.beaconfire.shoppingapp.controllers.order.OrderController.getOrderById(..))")
    public ResponseEntity<ResponseDto<OrderDetailDto>> orderOwnershipCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        // before
        var startTime = System.currentTimeMillis();
        logger.info("From OwnershipCheckAspect.orderOwnershipCheck: " + proceedingJoinPoint.getSignature());
        logger.info("Start time: " + startTime);

        Long orderId = (Long) proceedingJoinPoint.getArgs()[0];
        Authentication authentication = (Authentication) proceedingJoinPoint.getArgs()[1];
        var user = (UserDto) authentication.getDetails();
        boolean flag = true;
        if(orderId != null && !ownershipService.checkOrderOwnership(user.getId(), orderId)) flag = false;

        try {
            if(!flag){
                throw new NotHaveAccessException("You don't have access to this resource!");
            }

            //Invoke the actual object
            ResponseEntity<ResponseDto<OrderDetailDto>> orderDetail = (ResponseEntity<ResponseDto<OrderDetailDto>>) proceedingJoinPoint.proceed();

            // after
            var endTime = System.currentTimeMillis();
            logger.info("End time: " + endTime);
            logger.info("Execution time: " + ((endTime - startTime) / 1000) + "seconds");
            return orderDetail;
        } catch (NotHaveAccessException e) {
            logger.error("User id " + user.getId() + " failed tried to access orderId " + orderId);
            throw e;
        }
    }
}
