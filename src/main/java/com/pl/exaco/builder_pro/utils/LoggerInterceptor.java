package com.pl.exaco.builder_pro.utils;

import com.pl.exaco.builder_pro.utils.logger.LoggerController;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoggerInterceptor extends HandlerInterceptorAdapter {


    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {
        LoggerController.logger.debug(response.toString());
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) {

        HttpServletRequest requestCacheWrapperObject
                = new ContentCachingRequestWrapper(request);
        requestCacheWrapperObject.getParameterMap();
        // Read inputStream from requestCacheWrapperObject and log it


        LoggerController.logger.debug(request.toString());
        return true;
    }
}