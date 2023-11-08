package com.proj.music.spotify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

public class WebConfig {
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();

		// Allow requests from your React app
		config.addAllowedOrigin("http://localhost:4200");

		// You can add more configuration options as needed

		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
