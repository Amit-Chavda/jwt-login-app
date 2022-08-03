package com.springjwt.filter;

import java.io.IOException;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springjwt.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springjwt.util.JwtUtil;

public class CustomAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger logger = LoggerFactory.getLogger(CustomAuthFilter.class);
    private final AuthenticationManager authenticationManager;

    public CustomAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");


        logger.warn("{} attempted to login!",username);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username,password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {


        //1000 * 60 * 10 = 10 minutes
        Date expiry = new Date(System.currentTimeMillis() + 1000 * 60 * 10); //10 minutes
        int cookieExpiry = 60 * 10;

        String rememberMe = request.getParameter("rememberMe");
        if (rememberMe != null && rememberMe.equals("on")) {
            expiry = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 5);//5 days
            cookieExpiry = 60 * 60 * 24 * 5;
        }

        String token = new JwtUtil().generateToken(
                (User) authResult.getPrincipal(),
                expiry);
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(cookieExpiry);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        super.successfulAuthentication(request, response, chain, authResult);
        logger.info("{} logged in successfully!",authResult.getName());
    }

}
