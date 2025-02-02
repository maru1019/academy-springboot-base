package com.spring.springbootapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.springbootapplication.dao.SkillMapper;
import com.spring.springbootapplication.dao.CategoryMapper;
import com.spring.springbootapplication.dto.SkillRequest;
import com.spring.springbootapplication.entity.CategoryEntity;
import com.spring.springbootapplication.entity.SkillEntity;
import com.spring.springbootapplication.enums.Category;
import com.spring.springbootapplication.service.CategoryService;



import java.time.LocalDateTime;
import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillMapper skillMapper;
    
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryService categoryService;
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
     * @param categoryId カテゴリ（backend, frontend, infra）
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

    /**
     * 新規スキル登録
     * @param skillRequest 登録データ
     */
    public void save(Integer userId, SkillRequest skillRequest) {

        // カテゴリIDが有効かを確認
        Integer categoryId = skillRequest.getCategoryId();
        CategoryEntity category = categoryMapper.findById(categoryId);
        if (category == null) {
            throw new IllegalArgumentException("指定されたカテゴリが見つかりません");
        }

        // SkillRequest から SkillEntity を作成
        SkillEntity skillEntity = new SkillEntity();
        skillEntity.setUserId(userId); // userId をセット
        skillEntity.setCategoryId(categoryId);
        skillEntity.setName(skillRequest.getName());
        skillEntity.setStudyTime(skillRequest.getStudyTime());
        skillEntity.setCreateMonth(skillRequest.getCreateMonth());

        // SkillEntity を MyBatis に渡して保存
        skillMapper.save(skillEntity);
    }
}
