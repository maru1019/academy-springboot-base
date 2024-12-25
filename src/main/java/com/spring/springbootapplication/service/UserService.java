package com.spring.springbootapplication.service;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.springbootapplication.dao.UserMapper;
import com.spring.springbootapplication.dto.UserNewAddRequest;
import com.spring.springbootapplication.dto.UserResponse;
import com.spring.springbootapplication.dto.UserEditRequest;

import com.spring.springbootapplication.entity.UserEntity;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;


@Service
public class UserService {
  
  @Autowired
  private UserMapper userMapper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  // ユーザー取得
  public UserResponse getUserById(Integer id) {
    UserResponse user = userMapper.findById(id);
    return user;
  }

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
  
  // 編集機能
  public void update(UserEditRequest userEditRequest) {
    userMapper.update(userEditRequest);
  }

  // ファイルをバイト配列に変換
  public byte[] convertFileToByteArray(MultipartFile imageFile) {
    try {
        if (imageFile != null && !imageFile.isEmpty()) {
            return imageFile.getBytes(); // ファイルをバイト配列に変換
        }
    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("ファイル変換に失敗しました");
    }
    return null; // 画像がアップロードされなかった場合
  }
}
