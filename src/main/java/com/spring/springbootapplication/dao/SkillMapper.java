package com.spring.springbootapplication.dao;

import org.apache.ibatis.annotations.Mapper;
import com.spring.springbootapplication.dto.SkillRequest;
import com.spring.springbootapplication.entity.SkillEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;



@Mapper
public interface SkillMapper {

    // 指定した月とユーザーIDでデータを検索
    List<SkillEntity> findByMonthAndUser(@Param("month") Integer month, @Param("userId") Integer userId);

    // void save(SkillRequest skillRequest);
    
}