package org.JamesWeb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class PageableConfig implements WebMvcConfigurer {

    public PageableConfig() {
        System.out.println("PageableConfig Loaded");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        System.out.println("Registering PageableHandlerMethodArgumentResolver");
        resolvers.add(new PageableHandlerMethodArgumentResolver());
    }
}