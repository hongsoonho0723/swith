package com.sportsmania.swith.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("classpath:/static/assets/")
                .setCachePeriod(31556926);
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/teams/assets/js/upload.js").setViewName("teams/sp-modify");
        registry.addViewController("/teams/assets/js/supportteam.js").setViewName("teams/sp-modify");
        registry.addViewController("/teams/assets/css/swith.css").setViewName("teams/sp-modify");
        registry.addViewController("/teams/assets/img/swith/swith-picture.png").setViewName("teams/sp-modify");
        registry.addViewController("/teams/assets/favicon/site.webmanifest").setViewName("teams/sp-modify");
    }
}