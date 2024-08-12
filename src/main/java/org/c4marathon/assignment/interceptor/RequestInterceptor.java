package org.c4marathon.assignment.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.c4marathon.assignment.exception.IllegalParameterException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("[uri] : {}", request.getRequestURI());
        log.info("[user-key] : {}", request.getHeader("X-USER-KEY"));
        String userKey = request.getHeader("X-USER-KEY");
        if(userKey == null || userKey.length() == 0){
            throw new IllegalParameterException();
        }
        return true;
    }
}
