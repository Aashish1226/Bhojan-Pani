package Food.FoodDelivery.project.Util;

import Food.FoodDelivery.project.service.JwtService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String userUUID = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
            try {
                userUUID = jwtService.extractUUID(jwtToken);
            } catch (Exception e) {
                log.error("Invalid JWT token: {}", e.getMessage());
            }
        }

        if (userUUID != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtService.validateToken(jwtToken)) {

                String role = jwtService.extractRole(jwtToken);
                List<String> permissions = jwtService.extractPermissions(jwtToken);

                List<GrantedAuthority> authorities = new ArrayList<>();
                if (role != null) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
                }
                if (permissions != null) {
                    permissions.forEach(permission ->
                            authorities.add(new SimpleGrantedAuthority(permission)));
                }

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userUUID, null, authorities);

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

                request.setAttribute("userUUID", userUUID);

                log.debug("Authentication set for user UUID: {}", userUUID);
            } else {
                log.warn("Invalid or expired JWT token for UUID: {}", userUUID);
            }
        }

        filterChain.doFilter(request, response);
    }
}