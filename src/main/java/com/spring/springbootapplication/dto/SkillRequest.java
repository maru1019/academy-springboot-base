package com.spring.springbootapplication.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class SkillRequest implements Serializable {

  private Integer id;
    private Integer createMonth;
    private String name;
    private Integer studyTime;
    private Integer userId;
    private Integer categoryId;
}
