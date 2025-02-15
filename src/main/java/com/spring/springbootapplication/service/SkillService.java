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

import java.time.LocalDate;
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
     * 指定したカテゴリ、月、ユーザーIDでデータを取得
     * @param categoryId カテゴリID
     * @param createMonth 月
     * @param userId ユーザーID
     * @return 指定されたカテゴリのスキルリスト
     */
    public List<SkillEntity> getSkillsByCategoryAndMonth(Integer categoryId, Integer createMonth, Integer userId) {
        return skillMapper.findByCategoryAndMonth(categoryId, createMonth, userId);
    }

    /**
     * ユーザーIDとカテゴリごとに、同じ名前のスキルが存在するかチェック
     * @param name スキル名
     * @param userId ユーザーID
     * @param createMonth 月
     */
    public boolean existsByNameAndUserAndMonth(String name, Integer userId, Integer createMonth) {
        return skillMapper.countByNameUserAndMonth(name, userId, createMonth) > 0;
    }
    
    /**
     * 新規スキル登録
     * @param skillRequest 登録データ
     */
    public void save(Integer userId, SkillRequest skillRequest) {

        if (skillRequest.getCategoryId() == null) {
            throw new IllegalArgumentException("カテゴリ ID が設定されていません");
        }

        // SkillRequest から SkillEntity を作成
        SkillEntity skillEntity = new SkillEntity();
        skillEntity.setUserId(userId); 
        skillEntity.setCategoryId(skillRequest.getCategoryId());
        skillEntity.setName(skillRequest.getName());
        skillEntity.setStudyTime(skillRequest.getStudyTime());
        skillEntity.setCreateMonth(skillRequest.getCreateMonth() != null ? skillRequest.getCreateMonth() : LocalDate.now().getMonthValue());

        // SkillEntity を MyBatis に渡して保存
        skillMapper.save(skillEntity);
    }

    public void update (Integer userId, SkillRequest skillRequest) {

        SkillEntity skillEntity = new SkillEntity();
        skillEntity.setUserId(userId);
        skillEntity.setCategoryId(skillRequest.getCategoryId());
        skillEntity.setStudyTime(skillRequest.getStudyTime());
        skillEntity.setCreateMonth(skillRequest.getCreateMonth());

        skillMapper.update(skillEntity);
    }
}
