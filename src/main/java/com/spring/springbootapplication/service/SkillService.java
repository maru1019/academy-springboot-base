package com.spring.springbootapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.springbootapplication.dao.SkillMapper;
import com.spring.springbootapplication.dto.SkillRequest;
import com.spring.springbootapplication.entity.SkillEntity;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


@Service
public class SkillService {

    @Autowired
    private SkillMapper skillMapper;

    /**
     * 指定した月とユーザーIDでデータを取得（共通メソッド）
     * @param userId ユーザーID
     * @param createMonth 月
     * @return スキルのリスト
     */
    public List<SkillEntity> getSkillsByMonthAndUser(Integer createMonth, Integer userId) {
        // データベースからデータ取得
        List<SkillEntity> skills = skillMapper.findByMonthAndUser(createMonth, userId);

        // データが空の場合、ダミーデータを生成
        // if (skills.isEmpty()) {
        //     skills = createDummySkills(createMonth, userId);
        // }
        return skills;
    }

    /**
     * 当月データを取得する共通メソッド
     * @param userId ユーザーID
     * @return スキルのリスト
     */
    public List<SkillEntity> getCurrentMonthSkills(Integer userId) {
        int currentMonth = LocalDate.now().getMonthValue(); // 当月
        return getSkillsByMonthAndUser(currentMonth, userId);
    }

    // ダミーデータを生成するメソッド（変更なし）
    // private List<SkillEntity> createDummySkills(Integer createMonth, Integer userId) {
    //     List<SkillEntity> dummySkills = new ArrayList<>();
    //     for (int i = 1; i <= 3; i++) {
    //         SkillEntity skill = new SkillEntity();
    //         skill.setId(i); // 仮のID
    //         skill.setCreateMonth(createMonth);
    //         skill.setName("Dummy Skill " + i);
    //         skill.setStudyTime(0); // ダミーデータなので初期値0
    //         skill.setUserId(userId);
    //         skill.setCategoryId(1); // 仮のカテゴリID
    //         dummySkills.add(skill);
    //     }
    //     return dummySkills;
    // }
}



