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
                .requestMatchers( "/user/login", "/user/add", "/css/**", "/js/**").permitAll() 
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/user/login")  // ログインページのURL
                .loginProcessingUrl("/process-login") // ログインフォームの送信先
                .usernameParameter("email") //ログインページのユーザーID
                .passwordParameter("password") //ログインページのパスワード
                .defaultSuccessUrl("/user/top", true)  // ログイン成功後のリダイレクト先
                .failureUrl("/user/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // ユーザーがログアウトするためのURLを定義
                .logoutSuccessUrl("/user/login") // ログアウト成功後のリダイレクト先
                .permitAll()// ログアウトURLへのアクセスを認証なしで許可
            );
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
  
}
