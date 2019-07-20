package com.kakao.bookSearch.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
	/*
	 * @Autowired
	 * 
	 * @Qualifier(value = "routerInterceptor") private HandlerInterceptor
	 * interceptor;
	 */
	
	/**
	 * Interceptor가 가능 하도록 등록 시켜 준다.
	 */
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
    	
        registry.addInterceptor(new RouterInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**")
        		.excludePathPatterns("/error/**");	
                //.excludePathPatterns("/login"); //로그인 쪽은 예외처리를 한다.
    }
}