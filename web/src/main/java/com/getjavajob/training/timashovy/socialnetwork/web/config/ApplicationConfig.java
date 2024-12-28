package com.getjavajob.training.timashovy.socialnetwork.web.config;

import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
@ComponentScan(basePackages = {
        "com.getjavajob.training.timashovy.socialnetwork.web",
        "com.getjavajob.training.timashovy.socialnetwork.service",
        "com.getjavajob.training.timashovy.socialnetwork.dao"
})
@EnableWebMvc
public class ApplicationConfig implements WebMvcConfigurer {

    private static final Logger logger = getLogger(ApplicationConfig.class);

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/WEB-INF/jsp/");
        internalResourceViewResolver.setSuffix(".jsp");
        return internalResourceViewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

}
