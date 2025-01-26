package com.spring.springbootapplication.service;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.springbootapplication.dao.UserMapper;
import com.spring.springbootapplication.dto.UserNewAddRequest;
import com.spring.springbootapplication.dto.UserResponse;
import com.spring.springbootapplication.dto.UserEditRequest;

import com.spring.springbootapplication.entity.UserEntity;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;
import java.util.Base64;


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

  // -----Base64エンコードされた画像を取得-----
  public UserResponse getUserById(Integer id) {
    UserEntity userEntity = userMapper.findById(id); // DBからユーザー情報を取得
    UserResponse response = new UserResponse();
    response.setId(userEntity.getId());
    response.setBiography(userEntity.getBiography());

    // バイナリデータをBase64エンコード
    if (userEntity.getData() != null) {
        String base64Data = Base64.getEncoder().encodeToString(userEntity.getData());
        response.setBase64ImageData("data:image/png;base64," + base64Data); 
    }

    return response;
  }
}
