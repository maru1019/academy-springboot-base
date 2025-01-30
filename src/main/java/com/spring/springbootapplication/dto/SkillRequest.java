package com.spring.springbootapplication.dto;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class SkillRequest implements Serializable {

  private Integer id;

  @NotNull(message = "月を選択してください")
  private Integer createMonth;

  @NotBlank(message = "項目名は必ず入力してください")
  @Size(max = 50, message = "項目名は50文字以内で入力してください")
  private String name;

  @NotNull(message = "学習時間は必ず入力してください")
  @Min(value = 0, message = "学習時間は0以上の数字で入力してください")
  private Integer studyTime;

  private Integer userId;

  private Integer categoryId;

  private LocalDate createdAt;
  private LocalDate updatedAt;

  private String categoryName;

}

