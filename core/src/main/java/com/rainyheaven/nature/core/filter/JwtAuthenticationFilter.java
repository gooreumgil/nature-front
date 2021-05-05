package com.rainyheaven.nature.core.filter;

import com.rainyheaven.nature.core.domain.user.TokenUser;
import com.rainyheaven.nature.core.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        Authentication authentication = null;

        try {
            authentication = getAuthentication(request);
        } catch (ExpiredJwtException | MalformedJwtException e) {
            PrintWriter writer = response.getWriter();
            response.setStatus(401);
            writer.write(e.getMessage());
            writer.flush();
            writer.close();
        }

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    private Authentication getAuthentication(HttpServletRequest request) {

        String token = request.getHeader("Authorization");

        if (token == null || token.equals("")) return null;

        Claims claims = jwtUtil.getClaims(token.substring("Bearer ".length()));

        Set<GrantedAuthority> roles = new HashSet<>();
        String role = String.valueOf(claims.get("role"));
        roles.add(new SimpleGrantedAuthority("ROLE_" + role));
        TokenUser tokenUser = new TokenUser(claims);

        return new UsernamePasswordAuthenticationToken(tokenUser, null, roles);


    }
}
