package com.spring.springbootapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.springbootapplication.dao.SkillMapper;
import com.spring.springbootapplication.dto.SkillRequest;
import com.spring.springbootapplication.entity.SkillEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillMapper skillMapper;

    // 指定した月とユーザーIDのデータを取得
    public List<SkillEntity> getSkillsByMonthAndUser(Integer createMonth, Integer userId) {
        // データベースからデータ取得
        List<SkillEntity> skills = skillMapper.findByMonthAndUser(createMonth, userId);

        // データが空の場合、ダミーデータを生成
        if (skills.isEmpty()) {
            skills = createDummySkills(createMonth, userId);
        }

        return skills;
    }

    // 修正: ダミーデータを生成するメソッド
    private List<SkillEntity> createDummySkills(Integer createMonth, Integer userId) {
        List<SkillEntity> dummySkills = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            SkillEntity skill = new SkillEntity();
            skill.setId(i); // 仮のID
            skill.setCreateMonth(createMonth);
            skill.setName("Dummy Skill " + i);
            skill.setStudyTime(0); // ダミーデータなので初期値0
            skill.setUserId(userId);
            skill.setCategoryId(1); // 仮のカテゴリID
            dummySkills.add(skill);
        }
        return dummySkills;
    }

  // public void save(SkillRequest skillRequest) {
  //   skillMapper.save(skillRequest);
  // }
  
}


