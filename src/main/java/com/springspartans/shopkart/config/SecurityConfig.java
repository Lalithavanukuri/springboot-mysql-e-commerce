package com.springspartans.shopkart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) 
    		throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll()			// Allow all requests without authentication
            )
            .csrf(csrf -> csrf.disable())			// Optionally disable CSRF protection
            .formLogin(form -> form
                .disable() 							// Disable default form login
            )
            .logout(logout -> logout.disable())		// Disable default logout
            .httpBasic(basic -> basic.disable());	// Disable HTTP Basic authentication									
    	
    	return http.build();
    }
    
    @Bean
    protected HttpFirewall allowDoubleSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedDoubleSlash(true);
        return firewall;
    }
    
}
