package com.beaconfire.shoppingapp.aspects;

import com.beaconfire.shoppingapp.dtos.ResponseDto;
import com.beaconfire.shoppingapp.dtos.order.OrderDetailDto;
import com.beaconfire.shoppingapp.exceptions.NotEnoughInventoryException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect @Component
public class OrderAspect {
    private final Logger logger = LoggerFactory.getLogger(OrderAspect.class);

    @Around("execution(* com.beaconfire.shoppingapp.services.order.OrderService.placeOrderFromCart(..))")
    public ResponseDto<OrderDetailDto> logStartAndEndTimeForOrdering(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        try {
            // before
            var startTime = System.currentTimeMillis();
            logger.info("From OrderAspect.logStartAndEndTimeForOrdering: " + proceedingJoinPoint.getSignature());
            logger.info("Start time: " + startTime);

            //Invoke the actual object
            ResponseDto<OrderDetailDto> orderDetail = (ResponseDto<OrderDetailDto>) proceedingJoinPoint.proceed();

            // after
            var endTime = System.currentTimeMillis();
            logger.info("End time: " + endTime);
            logger.info("Execution time: " + ((endTime - startTime) / 1000) + "seconds");
            return orderDetail;
        } catch (NotEnoughInventoryException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }
}
