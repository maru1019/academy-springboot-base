package com.spring.springbootapplication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


import com.spring.springbootapplication.dao.UserMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserMapper userMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/user/**", "/css/**", "/js/**").permitAll()
                // .requestMatchers( "/user/login", "/user/add", "/css/**", "/js/**").permitAll() 
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/user/login")  // ログインページのURL
                .loginProcessingUrl("/process-login") // ログインフォームの送信先
                .usernameParameter("email") //ログインページのユーザーID
                .passwordParameter("password") //ログインページのパスワード
                .successHandler(customAuthenticationSuccessHandler())  // ログイン成功後のリダイレクト先
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
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.Authentication authentication) throws IOException, ServletException {
                // 認証されたユーザー名を取得
                String email = authentication.getName();

                // UserMapper を使ってユーザーIDを取得
                Integer userId = userMapper.findIdByEmail(email);
                if (userId == null) {
                    throw new ServletException("ユーザーIDが見つかりません");
                }

                // 動的リダイレクトURLを生成
                String redirectUrl = "/user/" + userId + "/top";

                // リダイレクト
                response.sendRedirect(redirectUrl);
            }
        };
    }
  
}
