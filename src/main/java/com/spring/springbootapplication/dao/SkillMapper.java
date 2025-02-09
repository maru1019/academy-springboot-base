package com.spring.springbootapplication.dao;

import org.apache.ibatis.annotations.Mapper;
import com.spring.springbootapplication.dto.SkillRequest;
import com.spring.springbootapplication.entity.SkillEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;



@Mapper
public interface SkillMapper {

    // 指定した月とユーザーIDでデータを検索
    List<SkillEntity> findByCategoryAndMonth(@Param("categoryId") Integer categoryId, 
    @Param("createMonth") Integer createMonth, 
    @Param("userId") Integer userId);

    // 同じ名前のスキルが存在するかチェック
    int countByNameAndUser(@Param("name") String name, 
    @Param("userId") Integer userId);

    // 同じカテゴリー内で名前が重複しているか確認するメソッド
    int countByNameAndUserWithCategory(@Param("name") String name,
    @Param("uesrId") Integer userId,
    @Param("categoryId") Integer categoryId );

    // 新規スキル登録
    void save(SkillEntity skillEntity);
        
}