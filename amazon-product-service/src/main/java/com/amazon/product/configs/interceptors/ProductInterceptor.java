package com.amazon.product.configs.interceptors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class ProductInterceptor implements HandlerInterceptor {

    Logger log = LogManager.getLogger(ProductInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("ProductInterceptor => Adding Interceptor");
        String uniqueId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", uniqueId);
        request.setAttribute("threadContextId", uniqueId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        log.info("ProductInterceptor => Controller Execution Completed");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        log.info("ProductInterceptor => Response Sent to Client");
        ThreadContext.clearAll();
    }
}
