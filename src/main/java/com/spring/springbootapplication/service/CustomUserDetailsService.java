package com.spring.springbootapplication.service;

import com.spring.springbootapplication.dao.UserMapper;
import com.spring.springbootapplication.entity.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userMapper.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("ユーザーが見つかりません: " + email);
        }

        return User.builder()
                .username(user.getEmail()) // メールアドレスをユーザー名として設定
                .password(user.getPassword()) // 暗号化済みのパスワード
                .authorities(Collections.emptyList()) // ロールを設定（現状空リスト）
                .build();
    }
}