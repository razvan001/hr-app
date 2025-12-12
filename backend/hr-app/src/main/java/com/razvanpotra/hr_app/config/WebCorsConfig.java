package com.razvanpotra.hr_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebCorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        // List your Angular origins (prod + dev)
        .allowedOrigins(
            "http://localhost:4200"
        )
        // Methods you use
        .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
        // Headers: include Authorization for Keycloak token
        .allowedHeaders("Authorization","Content-Type","Accept","X-Requested-With")
        .maxAge(86400); // cache preflight for 24h
  }
}

