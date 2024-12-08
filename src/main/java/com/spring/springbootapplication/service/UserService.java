package com.spring.springbootapplication.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.springbootapplication.dao.UserMapper;
import com.spring.springbootapplication.dto.UserNewAddRequest;
import com.spring.springbootapplication.entity.UserEntity;

import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UserService {
  
  @Autowired
  private UserMapper userMapper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  // ログイン機能
  public UserEntity getUserByEmail(String email) {
      return userMapper.findByEmail(email);
  }

  // 新規登録機能
  public void save(UserNewAddRequest userNewAddRequest) {

    String encodedPassword = passwordEncoder.encode(userNewAddRequest.getPassword());
    userNewAddRequest.setPassword(encodedPassword);

    userMapper.save(userNewAddRequest);
  }
}
