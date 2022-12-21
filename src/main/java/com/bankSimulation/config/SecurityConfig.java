package com.bankSimulation.config;

import com.bankSimulation.service.SecurityService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final SecurityService securityService;
    private final AuthSuccessHandler authSuccessHandler;

    public SecurityConfig(SecurityService securityService, AuthSuccessHandler authSuccessHandler) {
        this.securityService = securityService;
        this.authSuccessHandler = authSuccessHandler;
    }


    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .antMatchers("/accounts/**").hasAnyAuthority("Admin")
                .antMatchers("/transaction/**").hasAnyAuthority("Admin","Cashier")
                .antMatchers("/", "/login").permitAll()
                .anyRequest()
                .authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(authSuccessHandler)
                .failureUrl("/login?error=true")
                .permitAll()
            .and()
                .rememberMe()
                .tokenValiditySeconds(300)
                .key("bankapp")
                .userDetailsService(securityService)
                .and().build();

    }

}