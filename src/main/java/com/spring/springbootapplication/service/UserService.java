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

  //自己紹介編集機能
  public void update(UserEditRequest userEditRequest) {
    if (userEditRequest.getData() == null) {
        UserResponse existingUser = getUserById(userEditRequest.getId());

        if (existingUser.getData() != null) {
            userEditRequest.setData(existingUser.getData()); // 既存の画像データを保持
        } else {
            // デフォルト画像をセット
            userEditRequest.setData(loadDefaultImage());
        }
    }
    userMapper.update(userEditRequest);
  }

  // デフォルト画像をバイト配列として読み込むメソッド
  private byte[] loadDefaultImage() {
      try {
          Path defaultImagePath = Paths.get("src/main/resources/static/images/image.png");
          return Files.readAllBytes(defaultImagePath);
      } catch (Exception e) {
          e.printStackTrace();
          return null; // デフォルト画像の読み込みに失敗した場合は null を返す
      }
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
    UserEntity userEntity = userMapper.findById(id);
    UserResponse response = new UserResponse();
    response.setId(userEntity.getId());
    response.setName(userEntity.getName());
    response.setBiography(userEntity.getBiography());

    if (userEntity.getData() != null) {
        response.setBase64ImageData("data:image/png;base64," + Base64.getEncoder().encodeToString(userEntity.getData()));
        response.setData(userEntity.getData()); // 画像データもセット
    }

    return response;
  }

}
