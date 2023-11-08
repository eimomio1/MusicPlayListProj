package com.proj.music.spotify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow requests from your React app
        config.addAllowedOrigin("http://localhost:3000");

        // Allow GET and POST requests
        config.setAllowedMethods(Arrays.asList("GET", "POST"));

        // You can add more configuration options as needed

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
