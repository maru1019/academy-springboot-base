package com.spring.springbootapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.springbootapplication.dao.SkillMapper;
import com.spring.springbootapplication.dto.SkillRequest;
import com.spring.springbootapplication.entity.SkillEntity;


@Service
public class SkillService {

  @Autowired
  private SkillMapper skillMapper;

  public SkillEntity getSkillById(Integer id) {
    return skillMapper.findById(id);
  }

  // SkillService に変換メソッドを追加
  public SkillRequest converToSkillRequest(SkillEntity entity) {
    SkillRequest request = new SkillRequest();
    request.setId(entity.getId());
    request.setCreate_month(entity.getCreateMonth());
    request.setName(entity.getName());
    request.setStudy_time(entity.getStudyTime());
    request.setUser_id(entity.getUserId());
    request.setCategory_id(entity.getCategoryId());
    return request;
  }

  public void save(SkillRequest skillRequest) {
    skillMapper.save(skillRequest);
  }
  
}


