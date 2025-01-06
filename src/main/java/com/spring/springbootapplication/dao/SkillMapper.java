package com.spring.springbootapplication.dao;

import org.apache.ibatis.annotations.Mapper;
import com.spring.springbootapplication.dto.SkillRequest;
import com.spring.springbootapplication.entity.SkillEntity;

@Mapper
public interface SkillMapper {

    SkillEntity findById(Integer id);

    void save(SkillRequest skillRequest);
    
}