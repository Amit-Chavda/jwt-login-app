package com.springjwt.config;

import com.springjwt.service.UserService;
import org.springframework.context.annotation.Bean;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.springjwt.filter.CustomAuthFailure;
import com.springjwt.filter.CustomAuthFilter;
import com.springjwt.filter.JwtAuthFilter;

@EnableWebSecurity
public class WebSecurityConfig {


    private UserService customUserDetailsService;
    private JwtAuthFilter authFilter;
    private CustomAuthFailure authFailure;
    private AuthenticationConfiguration authenticationConfiguration;

    public WebSecurityConfig(UserService customUserDetailsService, JwtAuthFilter authFilter, CustomAuthFailure authFailure) {
        this.customUserDetailsService = customUserDetailsService;
        this.authFilter = authFilter;
        this.authFailure = authFailure;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable();

        http.authorizeRequests()
                .antMatchers("/login", "/forgotPassword", "/resetPassword", "/register", "/loginError").permitAll()
                .antMatchers("/loginError").permitAll()
                .anyRequest().authenticated();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/login")
                .failureHandler(authFailure);

        http.addFilter(new CustomAuthFilter(authenticationManager()));

        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
