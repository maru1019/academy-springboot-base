package com.spring.springbootapplication.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class UserResponse implements Serializable {

  private Integer id;
  private String biography;
  private String imageUrl;

}

