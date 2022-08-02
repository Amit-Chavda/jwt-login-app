package com.springjwt.filter;

import com.springjwt.util.JwtUtil;
import com.springjwt.service.CustomUserDetailsService;
import com.springjwt.util.CookieUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;
    private CustomUserDetailsService userDetailsService;

    public JwtAuthFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        JwtUtil jwtUtil=new JwtUtil();
        String cookieName = "token";
        String username=null;

        String header = request.getHeader("Authorization");
        String token = CookieUtil.getCookieValueByName(request, cookieName);

        if (token != null && !jwtUtil.isTokenExpired(token)) {
            username = jwtUtil.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                throw new ServletException("Token Expired or Invalid token passed...");
            }

        } else if (header != null && header.startsWith("Bearer")) {
            token = header.substring(7);

            try {
                username = jwtUtil.extractUsername(token);
            }catch (Exception exception){
                //SignatureException when different secret key is used
                //JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted.
                logger.error(exception.getMessage());
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                throw new ServletException("Token Expired or Invalid token passed...");
            }

        }

        filterChain.doFilter(request, response);
    }
}
