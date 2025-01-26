package com.spring.springbootapplication.service;

import com.spring.springbootapplication.entity.CategoryEntity;
import com.spring.springbootapplication.dao.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public CategoryEntity getCategoryByUserId(Integer userId) {
        return categoryMapper.findByUserId(userId);
    }
}
