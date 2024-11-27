package com.beaconfire.shoppingapp.interceptors;

import com.beaconfire.shoppingapp.dtos.account.user.UserDto;
import com.beaconfire.shoppingapp.services.account.OwnershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Component
public class OwnershipInterceptor  implements HandlerInterceptor {
    private OwnershipService ownershipService;

    @Autowired
    private void setOwnershipService(OwnershipService ownershipService){
        this.ownershipService = ownershipService;
    }

    private void reject(HttpServletResponse response) throws Exception{
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("You don't have access to this resource!");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // PathVariables are available at Interceptor, not WebFilter since they haven't reached DispatcherServlet for parsing
        var pathVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
//        System.out.println(pathVars);
        String templateId = pathVars.get("templateId");
        String orderId = pathVars.get("orderId");
        String watchListProductId = pathVars.get("watchListProductId");
        if(templateId == null && orderId == null && watchListProductId == null) return true;

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null){
            UserDto user = (UserDto) session.getAttribute("user");
            if(user.isAdmin()) return true;

            if(templateId != null && !ownershipService.checkOrderTemplateOwnership(user.getId(), Long.parseLong(templateId))){
                reject(response);
                return false;
            }
            if(orderId != null && !ownershipService.checkOrderOwnership(user.getId(), Long.parseLong(orderId))){
                reject(response);
                return false;
            }
//            if(watchListProductId != null && !ownershipService.checkWatchListOwnership(user.getId(), Long.parseLong(watchListProductId))){
//                reject(response);
//                return false;
//            }

            return true;
        }

        reject(response);
        return false;
    }
}
