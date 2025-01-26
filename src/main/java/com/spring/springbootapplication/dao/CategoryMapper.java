package com.spring.springbootapplication.dao;

import com.spring.springbootapplication.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CategoryMapper {
    CategoryEntity findByUserId(@Param("userId") Integer userId);
}
