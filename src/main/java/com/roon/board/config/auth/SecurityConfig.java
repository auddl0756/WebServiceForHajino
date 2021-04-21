package com.roon.board.config.auth;

import com.roon.board.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.h2.engine.User;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity  //to have the Spring Security configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //public abstract class WebSecurityConfigurerAdapter
    //extends Object
    //implements o.s.s.c.a.w.WebSecurityConfigurer<org.springframework.security.config.annotation.web.builders.WebSecurity>
    //Provides a convenient base class for creating a WebSecurityConfigurer instance.

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //public final class HttpSecurity
        //extends org.springframework.security.config.annotation.AbstractConfiguredSecurityBuilder<org.springframework.security.web.DefaultSecurityFilterChain, HttpSecurity>
        //implements org.springframework.security.config.annotation.SecurityBuilder<org
        // .springframework.security.web.DefaultSecurityFilterChain>, org.springframework.security
        // .config.annotation.web.HttpSecurityBuilder<HttpSecurity>

        //It allows configuring web based security for specific http requests.
        //By default it will be applied to all requests, but can be restricted
        // using requestMatcher(RequestMatcher) or other similar methods.

        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                //Allows restricting access based upon the HttpServletRequest
                .authorizeRequests()
                .antMatchers("/","/css/**","/images/","/js/**","/h2-console/**").permitAll()
                .antMatchers("/posts/save").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()   //Specify that URLs are allowed by any authenticated user.
                .and()
                .logout()   //Provides logout support. This is automatically applied when using WebSecurityConfigurerAdapter.
                .logoutSuccessUrl("/")      //The URL to redirect to after logout has occurred.
                .and()
                .oauth2Login()  //Configures authentication support using an OAuth 2.0
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }
}
