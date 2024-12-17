package com.spring.springbootapplication.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.springbootapplication.dto.UserEditRequest;
import com.spring.springbootapplication.dto.UserNewAddRequest;
import com.spring.springbootapplication.dto.UserResponse;
import com.spring.springbootapplication.entity.UserEntity;


@Mapper
public interface UserMapper {

  // -----top画面------
  UserResponse findById(Integer id);

  // -----ログイン機能-----
  UserEntity findByEmail(String email);

  // -----新規登録機能-----
  void save(UserNewAddRequest userNewAddRequest);

  // -----編集機能------
  // UserResponse findById(Integer id);
  //   void update(UserEditRequest userEditRequest);

}
