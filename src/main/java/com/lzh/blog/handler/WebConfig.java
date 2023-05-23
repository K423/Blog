package com.lzh.blog.handler;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(new Interceptor())
                .addPathPatterns("/admin/**")
//                .addPathPatterns("/adminLogin")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");

    }
}
