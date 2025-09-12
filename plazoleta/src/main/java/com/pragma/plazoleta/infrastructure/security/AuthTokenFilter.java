package com.pragma.plazoleta.infrastructure.security;

import io.jsonwebtoken.Claims;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthTokenFilter.class);

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final int BEARER_PREFIX_LENGTH = 7;
    private static final String AUTHORITIES_CLAIM = "authorities";
    private static final String USER_ID_CLAIM = "userId";

    private final JwtUtil jwtUtils;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                Claims claims = jwtUtils.getClaims(jwt);

                String username = claims.getSubject();
                Long userId = getUserIdFromToken(claims);

                CustomUserDetails userDetails = new CustomUserDetails(
                        username,
                        null,
                        getAuthoritiesFromToken(claims),
                        userId
                );

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: ", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(AUTH_HEADER);

        if (headerAuth != null && headerAuth.startsWith(BEARER_PREFIX)) {
            return headerAuth.substring(BEARER_PREFIX_LENGTH);
        }

        return null;
    }

    private List<SimpleGrantedAuthority> getAuthoritiesFromToken(Claims claims) {
        if (isNull(claims.get(AUTHORITIES_CLAIM))) {
            log.error("Authorities not found");
            return Collections.emptyList();
        }

        @SuppressWarnings("unchecked")
        List<String> authorityList = (List<String>) claims.get(AUTHORITIES_CLAIM);

        return authorityList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    private Long getUserIdFromToken(Claims claims) {
        if (isNull(claims.get(USER_ID_CLAIM))) {
            log.error("User ID not found");
            return null;
        }

        return ((Number) claims.get(USER_ID_CLAIM)).longValue();
    }
}
