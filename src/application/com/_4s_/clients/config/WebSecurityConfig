package com._4s_.clients.config;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.*;
import static org.springframework.security.web.util.matcher.RegexRequestMatcher.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com._4s_.clients.dao.TenantRepository;
import com._4s_.clients.web.util.TenantAuthorizationFilter;
import com._4s_.clients.web.util.TenantFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final TenantRepository tenantRepository;

    public WebSecurityConfig(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .httpBasic()
                    .authenticationEntryPoint(httpStatusEntryPoint())
                    .and()
                .csrf()
                    .and()
                .addFilterBefore(new TenantFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new TenantAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .authorizeHttpRequests(
//                    auths ->
//                            auths
//                                .requestMatchers(
//                                        regexMatcher("/(posts|register)?"))
//                                    .permitAll()
//                                .requestMatchers(antMatcher(HttpMethod.GET, "/api/**"))
//                                    .permitAll()
//                                .requestMatchers(
//                                        antMatcher(HttpMethod.GET, "/js/**"),
//                                        antMatcher(HttpMethod.GET, "/css/**"),
//                                        antMatcher(HttpMethod.GET, "/webjars/**"),
//                                        antMatcher(HttpMethod.GET, "/style/**"),
//                                        regexMatcher(HttpMethod.GET, ".+\\.ico"))
//                                    .permitAll()
//                                .anyRequest()
//                                    .authenticated())
                .formLogin()
                    .loginPage("/login")
                        .permitAll()
                    .and()
                .logout()
                    .permitAll();
        // @formatter:on
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private HttpStatusEntryPoint httpStatusEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
    }
}
