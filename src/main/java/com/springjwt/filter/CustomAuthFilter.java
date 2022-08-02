package com.springjwt.filter;

import java.io.IOException;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springjwt.util.JwtUtil;

public class CustomAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomAuthFilter.class);
    private final AuthenticationManager authenticationManager;

    public CustomAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        LOGGER.warn(username + " attempted to login!");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {


        //1000 * 60 * 10 = 10 minutes
        String token = new JwtUtil().generateToken(
                (User) authResult.getPrincipal(),
                new Date(System.currentTimeMillis() + 1000 * 60 * 10));
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(60 * 10); // expires in 10 mins
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        super.successfulAuthentication(request, response, chain, authResult);
        LOGGER.info(authResult.getName() + " logged in successfully!");
    }

}
