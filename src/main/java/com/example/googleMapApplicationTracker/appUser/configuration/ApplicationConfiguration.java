package com.example.googleMapApplicationTracker.appUser.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.pattern.PathPatternParser;

import javax.servlet.Filter;

@Configuration
public class ApplicationConfiguration {
    //    @Bean
//    public CorsWebFilter corsWebFilter() {
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true); // you may not need this
//        config.addAllowedOrigin("*"); // or specify your frontend's URL for security
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
//        source.registerCorsConfiguration("/**", config);
//
//        return new CorsWebFilter(source);
//    }
//@Bean
//public WebMvcConfigurer configure() {
//    return new WebMvcConfigurer() {
//        @Override
//        public void addCorsMappings(CorsRegistry registry) {
//            registry.addMapping("/*").allowedOrigins("https://gamefool.gamefi.codes/");
//        }
//
//    };
//}
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Разрешить отправку cookies и authentication headers
        config.addAllowedOrigin("https://gamefool.gamefi.codes/"); // Замените на домен вашего WebGL приложения
        config.addAllowedHeader("*"); // Разрешить все заголовки
        config.addAllowedMethod("OPTIONS"); // Разрешить предварительные запросы CORS
        config.addAllowedMethod("GET"); // Разрешить GET запросы
        config.addAllowedMethod("POST"); // Разрешить POST запросы
        config.addAllowedMethod("PUT"); // Разрешить PUT запросы
        config.addAllowedMethod("DELETE"); // Разрешить DELETE запросы

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

//    @Bean
//    public Filter corsFilter() {
//        return new CorsFilter();
//    }
}
