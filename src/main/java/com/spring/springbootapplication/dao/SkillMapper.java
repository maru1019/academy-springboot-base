package com.spring.springbootapplication.dao;

import org.apache.ibatis.annotations.Mapper;
import com.spring.springbootapplication.dto.SkillRequest;
import com.spring.springbootapplication.entity.SkillEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper
public interface SkillMapper {

    SkillEntity findById(@Param("id") Integer id);

    // 指定した月とユーザーIDでデータを検索
    List<SkillEntity> findByCategoryAndMonth(@Param("categoryId") Integer categoryId, 
        @Param("createMonth") Integer createMonth, 
        @Param("userId") Integer userId);

    int countByNameUserAndMonth(@Param("name") String name,
        @Param("userId") Integer userId,
        @Param("createMonth") Integer createMonth);

    // 新規スキル登録
    void save(SkillEntity skillEntity);

    //学習時間編集
    void update(SkillEntity skillEntity);

    //削除
    void delete(SkillEntity skillEntity);

    // 過去3ヶ月の学習時間を取得
    List<Map<String, Object>> getTotalStudyTimeByCategory(
        @Param("categoryId") Integer categoryId,
        @Param("userId") Integer userId,
        @Param("months") List<Integer> months
    );
        
}