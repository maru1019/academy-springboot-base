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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SkillService {

    @Autowired
    private SkillMapper skillMapper;
    
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryService categoryService;


    public SkillEntity getSkillById (Integer skillId) {
        return skillMapper.findById(skillId);
    }

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

        if (skillRequest.getId() == null) {
            throw new IllegalArgumentException("スキル ID が設定されていません");
        }
        
        SkillEntity skillEntity = new SkillEntity();
        skillEntity.setId(skillRequest.getId());
        skillEntity.setUserId(userId);
        skillEntity.setCategoryId(skillRequest.getCategoryId());
        skillEntity.setCreateMonth(skillRequest.getCreateMonth());
        skillEntity.setStudyTime(skillRequest.getStudyTime());
    
        skillMapper.update(skillEntity);
    }

    public void delete (Integer userId, SkillRequest skillRequest) {

        if (skillRequest.getId() == null) {
            throw new IllegalArgumentException("スキル ID が設定されていません");
        }
        
        SkillEntity skillEntity = new SkillEntity();
        skillEntity.setId(skillRequest.getId());
        skillEntity.setUserId(userId);

        skillMapper.delete(skillEntity);
    
    }

    public Map<Integer, Integer> getTotalStudyTimeByCategory(Integer categoryId, Integer userId, List<Integer> months) {
        List<Map<String, Object>> results = skillMapper.getTotalStudyTimeByCategory(categoryId, userId, months);
    
        Map<Integer, Integer> studyTimeByMonth = new HashMap<>();
        for (Map<String, Object> result : results) {
            Integer month = (Integer) result.get("create_month");
            Number studyTimeNumber = (Number) result.get("total_study_time");
            Integer studyTime = studyTimeNumber.intValue();
            studyTimeByMonth.put(month, studyTime);
        }
        return studyTimeByMonth;
    }
    
    

    public List<Integer> getRecentMonths() {
        LocalDate now = LocalDate.now();
        List<Integer> months = new ArrayList<>();
    
        for (int i = 0; i < 3; i++) {
            LocalDate target = now.minusMonths(i);
            months.add(target.getMonthValue()); // MM を取得
        }

        System.out.println("Generated recent months: " + months); //デバック
        return months;
    }
    
}
