package com.spring.springbootapplication.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class CategoryEntity implements Serializable {

  private Integer id;

  private String name;

  private Date createdAt;

  private Date updatedAt;
  
}
