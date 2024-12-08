package com.spring.springbootapplication.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.springbootapplication.dto.UserNewAddRequest;
import com.spring.springbootapplication.entity.UserEntity;


@Mapper
public interface UserMapper {

  // -----ログイン機能-----
  UserEntity findByEmail(String email);

  // -----新規登録機能-----
  void save(UserNewAddRequest userNewAddRequest);

}
