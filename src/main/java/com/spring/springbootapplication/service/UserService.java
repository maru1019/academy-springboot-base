package com.spring.springbootapplication.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.springbootapplication.dao.UserMapper;
import com.spring.springbootapplication.dto.UserNewAddRequest;
import com.spring.springbootapplication.dto.UserResponse;
import com.spring.springbootapplication.dto.UserEditRequest;

import com.spring.springbootapplication.entity.UserEntity;

import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UserService {
  
  @Autowired
  private UserMapper userMapper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  // Top画面表示
  public UserResponse getUserById(Integer id) {
    UserResponse userResponse = userMapper.findById(id);
    if (userResponse == null) {
      throw new IllegalArgumentException("User not found for id: " + id);
    }
    return userResponse;
  }

  // public String getUserIdByEmail(String email) {
  //   UserResponse userResponse = userMapper.findByEmail(email);
  //   if (userResponse != null) {
  //       return userResponse.getId();
  //   } else {
  //       return null; // ユーザーが存在しない場合、nullを返す
  //   }
  

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
