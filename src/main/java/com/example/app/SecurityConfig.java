package com.example.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	// The dev profile sets this so a development preview can frame the app; production keeps DENY.
	@Value("${app.allow-framing:false}")
	private boolean allowFraming;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
		if (allowFraming) {
			http.headers((headers) -> headers.frameOptions((frame) -> frame.disable()));
		}
		return http.build();
	}

}
