package com.himsoomzzin.calender.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    @Qualifier(value = "loginJwtInterceptor")
    private HandlerInterceptor loginJwtInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginJwtInterceptor)
                .addPathPatterns("/api/v1/auth/test")
                .excludePathPatterns("/swagger-ui/**")
                .excludePathPatterns("/api/v1/auth/login")
                .excludePathPatterns("/api/v1/auth/logout");

        /*
         * registry.addInterceptor(commonInterceptor) .addPathPatterns("/**") // 추가할 url
         * 패턴 .excludePathPatterns("/user/**"); // 제외할 url 패턴
         */
    }
}
