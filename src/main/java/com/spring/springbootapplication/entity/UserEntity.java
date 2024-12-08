package com.spring.springbootapplication.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class UserEntity implements Serializable {

  private Integer id;

  private String name;

  private String email;

  private String password;

  private String biography;

  private String imageUrl;

  private Date createdAt;

  private Date updatedAt;

}
