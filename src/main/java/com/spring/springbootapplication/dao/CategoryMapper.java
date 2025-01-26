package com.spring.springbootapplication.dao;

import com.spring.springbootapplication.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CategoryMapper {
    List<CategoryEntity> findByUserId(@Param("userId") Integer userId);
}
