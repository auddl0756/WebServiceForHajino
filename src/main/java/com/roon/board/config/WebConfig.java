package com.roon.board.config;

import com.roon.board.config.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    //WebMvcConfigurer
    // : Defines callback methods to customize the Java-based configuration for Spring MVC enabled via @EnableWebMvc.

    private final LoginUserArgumentResolver loginArgsResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginArgsResolver);
    }
}
