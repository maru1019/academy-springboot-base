package com.spring.springbootapplication.service;

import com.spring.springbootapplication.entity.CategoryEntity;
import com.spring.springbootapplication.dao.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<CategoryEntity> getCategoriesByUserId(Integer userId) {
        return categoryMapper.findByUserId(userId);
    }

    /**
     * 指定された月のカテゴリ情報を取得
     * @param createMonth 月
     * @return カテゴリ情報
     */
    public CategoryEntity getSelectedCategory(String name) {
        return categoryMapper.findByMonth(name);
    }
}
