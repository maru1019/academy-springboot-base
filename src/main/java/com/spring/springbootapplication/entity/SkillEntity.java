package com.spring.springbootapplication.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class SkillEntity implements Serializable {

  private Integer id;

  private Integer createMonth;

  private String name;

  private Integer studyTime;

  private Date createdAt;

  private Date updatedAt;

  private Date deletedAt;

  private Integer userId; // 外部キー：ユーザーID

  private Integer categoryId; // 外部キー：カテゴリID

  // リレーションエンティティ
  private UserEntity user; // ユーザー情報（リレーション）
  private CategoryEntity category; // カテゴリ情報（リレーション）
  
}
