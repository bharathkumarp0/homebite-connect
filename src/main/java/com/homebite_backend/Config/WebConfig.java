package com.homebite_backend.Config;

import com.homebite_backend.Controllers.NoCacheInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/uploads/**")
//                .addResourceLocations("file:///C:/Users/bhara/Downloads/homebite-connect-backend/uploads/");

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///C:/Users/bhara/Downloads/homebite-connect-backend/src/main/resources/static/uploads/");

        registry.addResourceHandler("/myshare/**")
                .addResourceLocations("file:///C:/Users/bhara/Downloads/homebite-connect-backend/myshare/");
    }



    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers("/images/**", "/css/**", "/js/**", "/myswap/uploads/**", "/myshare/uploads/**");
    }


    @Autowired
    private NoCacheInterceptor noCacheInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(noCacheInterceptor)
                .addPathPatterns("/**");

}}
