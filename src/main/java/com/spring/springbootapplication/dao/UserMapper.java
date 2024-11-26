package com.spring.springbootapplication.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.springbootapplication.dto.UserNewAddRequest;
import com.spring.springbootapplication.entity.UserEntity;


@Mapper
public interface UserMapper {

  void save(UserNewAddRequest userNewAddRequest);

}
