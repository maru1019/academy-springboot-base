package com.spring.springbootapplication.dao;

import com.spring.springbootapplication.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    CategoryEntity findById(@Param("id") Integer id);

    CategoryEntity findByName(@Param("name") String name);
}
