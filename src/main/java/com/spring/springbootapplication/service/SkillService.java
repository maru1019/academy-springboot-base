package com.spring.springbootapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.springbootapplication.dao.SkillMapper;
import com.spring.springbootapplication.entity.SkillEntity;

import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillMapper skillMapper;

    /**
     * 指定した月とユーザーIDでデータを取得（既存メソッド）
     * @param userId ユーザーID
     * @param createMonth 月
     * @return スキルのリスト
     */
    public List<SkillEntity> getSkillsByMonthAndUser(Integer createMonth, Integer userId) {
        // データベースからデータ取得
        return skillMapper.findByMonthAndUser(createMonth, userId);
    }

    /**
     * 指定したカテゴリ、月、ユーザーIDでデータを取得（新しいメソッド）
     * @param category カテゴリ（backend, frontend, infra）
     * @param createMonth 月
     * @param userId ユーザーID
     * @return 指定されたカテゴリのスキルリスト
     */
    public List<SkillEntity> getSkillsByCategoryAndMonth(Integer categoryId, Integer createMonth, Integer userId) {
        // 既存メソッドを利用してすべてのスキルを取得
        List<SkillEntity> allSkills = getSkillsByMonthAndUser(createMonth, userId);

        // カテゴリでフィルタリング
        return allSkills.stream()
                .filter(skill -> categoryId.equals(skill.getCategoryId())) // カテゴリに一致するデータを抽出
                .toList(); // フィルタリング結果をリスト化
    }
}
