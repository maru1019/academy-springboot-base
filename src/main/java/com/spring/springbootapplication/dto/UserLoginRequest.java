package com.spring.springbootapplication.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class UserLoginRequest implements Serializable {
  
  private String email;

  private String password;
}
