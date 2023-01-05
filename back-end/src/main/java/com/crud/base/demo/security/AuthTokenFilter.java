package com.crud.base.demo.security;

import java.io.IOException;
import java.util.UUID;

import com.crud.base.demo.service.user.SearchUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthTokenFilter extends OncePerRequestFilter {
  @Autowired
  private JwtHundler jwtHundler;

  @Autowired
  private SearchUserService searchUser;

  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String jwt = parseJwt(request);
      if (jwt != null && jwtHundler.validateJwtToken(jwt)) {
        final String userId = jwtHundler.getUserIdFromJwtToken(jwt);
        final UserDetails userDetails = searchUser.findById(UUID.fromString(userId));

        if (userDetails == null){
          response.setStatus(HttpServletResponse.SC_FORBIDDEN);
          response.sendError(HttpServletResponse.SC_FORBIDDEN, "Error when checking authentication.");
        }else{
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                  userDetails,
                  null,
                  userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }else{
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      }
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7, headerAuth.length());
    }

    return null;
  }
}