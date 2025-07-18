package com.cqcskj.lbc.interceptors;

import com.cqcskj.lbc.utils.JwtUti;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class SignVerifyInterceptors  implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token=request.getHeader("Authorization");
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            return true;
        }
        try {
            Map<String, Object> map = JwtUti.verifyToken(token);
            System.out.println(map);
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }
}
