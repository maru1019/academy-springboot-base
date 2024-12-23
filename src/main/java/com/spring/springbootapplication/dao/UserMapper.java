package com.spring.springbootapplication.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.spring.springbootapplication.dto.UserEditRequest;
import com.spring.springbootapplication.dto.UserNewAddRequest;
import com.spring.springbootapplication.dto.UserResponse;
import com.spring.springbootapplication.entity.UserEntity;


@Mapper
public interface UserMapper {

  // -----ユーザー取得------
  UserResponse findById(Integer id);

  // -----ユーザーID取得機能-----
  @Select("SELECT id FROM users WHERE email = #{email}")
  Integer findIdByEmail(String email);

  // -----ログイン機能-----
  UserEntity findByEmail(String email);

  // -----新規登録機能-----
  void save(UserNewAddRequest userNewAddRequest);

  // -----編集機能------
  void update(UserEditRequest userEditRequest);
}
