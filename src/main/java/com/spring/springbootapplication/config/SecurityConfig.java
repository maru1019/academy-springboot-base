package com.spring.springbootapplication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.spring.springbootapplication.dao.UserMapper;

@Configuration
public class SecurityConfig {

    @Autowired
  private UserMapper userMapper;

  @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/user/top", "/user/login", "/user/add", "/css/**", "/js/**").permitAll() 
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/user/login")
                .loginProcessingUrl("/user/top")
                .usernameParameter("email") //ログインページのユーザーID
                .passwordParameter("password") //ログインページのパスワード
                .defaultSuccessUrl("/user/top", true)
                .failureUrl("/user/login?error=true")
                .permitAll()
            );
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
  
}
