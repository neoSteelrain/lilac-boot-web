package com.steelrain.springboot.lilac.config;

import com.steelrain.springboot.lilac.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/",
                                     "/member/registration",
                                     "/member/login",
                                     "/member/logout",
                                     "/member/duplicated-nickname/**",
                                     "/member/duplicated-email/**",
                                     "/video/**",
                                     "/search/**",
                                     "/favicon/**",
                                     "/error/**",
                                     "/*.ico",
                                     "/css/**",
                                     "/fonts/**",
                                     "/images/**",
                                     "/js/**",
                                     "/mail/**",
                                     "/scss/**");

    }
}
