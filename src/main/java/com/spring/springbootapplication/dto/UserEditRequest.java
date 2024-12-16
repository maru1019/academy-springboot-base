package com.spring.springbootapplication.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class UserEditRequest implements Serializable {

  private Integer id;
  private String biography;
  private String imageUrl;
  
}
