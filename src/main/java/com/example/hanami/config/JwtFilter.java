package com.example.hanami.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService  jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            @NonNull  HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
    final String authHeader=request.getHeader("Authorization");
    //final String refreshToken=request.getHeader("X-Refresh-Token");
    final String jwt;
    final String userEmail;
    if(authHeader == null || !authHeader.startsWith("Bearer")){
        filterChain.doFilter(request,response);
        return;
    }

    //Get the token from authHeader
    jwt=authHeader.substring(7);
    //get email from  token
    userEmail=jwtService.extractUsernameClaims(jwt);
    if(userEmail != null && SecurityContextHolder.getContext().getAuthentication()==null){
       //is user stored
        UserDetails userDetails=this.userDetailsService.loadUserByUsername(userEmail);
        //validate if token matches with the owner
        if(jwtService.isTokenValid(jwt,userDetails)){
            //object to update in security context
            UsernamePasswordAuthenticationToken authtoken =new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authtoken);


        }
        //next filter in filterchain
        filterChain.doFilter(request,response);
    }


    }
}
