package com.spring.springbootapplication.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserEditRequest implements Serializable {

  private Integer id;

  @Size(min = 50, max = 200, message = "自己紹介は50文字以上200文字以下で入力してください")
  @NotBlank(message = "")
  private String biography;

  private String imageUrl;

  private byte[] data;

  // アップロードされた画像ファイルを受け取る
  private MultipartFile imageFile;
  
}
