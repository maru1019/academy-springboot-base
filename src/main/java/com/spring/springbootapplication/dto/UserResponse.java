package com.spring.springbootapplication.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UserResponse implements Serializable {

  private Integer id;
  private String name;
  private String biography;
  private String imageUrl;
  private byte[] data;
  private String base64ImageData; // Base64エンコードされたデータ（HTML表示用）

  public String getName() {
    return name;
  }

  public void setName(String name) {
      this.name = name;
  } 

}

