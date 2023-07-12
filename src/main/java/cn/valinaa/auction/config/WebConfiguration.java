package cn.valinaa.auction.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Valinaa
 * 
 * @Description:
 * @Date: 2023-07-03 11:04
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * Add handlers to serve static resources such as images, js, and, css
     * files from specific locations under web application root, the classpath,
     * and others.
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/picFiles/**").addResourceLocations("file:E:/JavaWorkPlace/PersonTest/auction/src/main/resources/static/imgs/goods/");
    }

}
