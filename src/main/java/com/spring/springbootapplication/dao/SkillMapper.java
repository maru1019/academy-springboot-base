package com.spring.springbootapplication.dao;

import org.apache.ibatis.annotations.Mapper;
import com.spring.springbootapplication.dto.SkillRequest;
import com.spring.springbootapplication.entity.SkillEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;



@Mapper
public interface SkillMapper {

    // 指定した月とユーザーIDでデータを検索
    List<SkillEntity> findByMonthAndUser(@Param("createMonth") Integer createMonth, @Param("userId") Integer userId);

    // 新規スキル登録
    void save(SkillEntity skillEntity);
    
}