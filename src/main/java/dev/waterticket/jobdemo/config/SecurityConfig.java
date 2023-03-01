package dev.waterticket.jobdemo.config;

import dev.waterticket.jobdemo.user.domain.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/api/**").hasRole(UserRole.ADMIN)
                .and()
                .csrf()
                .disable()
                .httpBasic();

        http.authorizeHttpRequests()
                .requestMatchers("/login", "/signup").permitAll()
                .requestMatchers("/admin/**").hasRole(UserRole.ADMIN)
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .defaultSuccessUrl("/")
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                .and()
                    .csrf()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        return http.build();
    }
}
