package com.spring.springbootapplication.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class SkillRequest implements Serializable {

  private Integer id;
  private Integer create_month;
  private String name;
  private Integer study_time;
  private Integer user_id;
  private Integer category_id;
  
}
