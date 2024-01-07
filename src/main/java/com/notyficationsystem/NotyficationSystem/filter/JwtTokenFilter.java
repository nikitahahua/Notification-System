package com.notyficationsystem.NotyficationSystem.filter;

import com.notyficationsystem.NotyficationSystem.service.impl.JwtServiceImpl;
import com.notyficationsystem.NotyficationSystem.service.impl.UserDetailsServiceImpl;
import com.notyficationsystem.NotyficationSystem.service.impl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.List;
import java.io.IOException;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtServiceImpl jwtService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.substring(7);
        UserDetails user = userDetailsService.loadUserByUsername(jwtService.extractUsername(token));
        if (jwtService.isTokenValid(token, user)) {
            UserDetails userDetails = userService
                    .readByEmail(jwtService.extractUsername(token));

            UsernamePasswordAuthenticationToken
                    authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null,
                    userDetails == null ?
                            List.of() : userDetails.getAuthorities()
            );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return;
        }

        chain.doFilter(request, response);
    }

}