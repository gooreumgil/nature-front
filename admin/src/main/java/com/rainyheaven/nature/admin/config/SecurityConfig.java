package com.rainyheaven.nature.admin.config;

import com.rainyheaven.nature.core.filter.JwtAuthenticationFilter;
import com.rainyheaven.nature.core.interceptor.JwtInterceptor;
import com.rainyheaven.nature.core.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtUtil jwtUtil;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/v1/session").permitAll()
                    .antMatchers("/**").hasAnyRole("ADMIN")
                .and().cors()
                .and().addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtUtil))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowedOrigins(allowedOrigins());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    private List<String> allowedOrigins() {
        return Arrays.asList(
                "http://localhost:8083",
                "http://nature-portfolio.rainy-heaven.com"
        );
    }


}
