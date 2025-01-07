package com.spring.springbootapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.springbootapplication.dto.SkillRequest;
import com.spring.springbootapplication.service.SkillService;
import com.spring.springbootapplication.entity.SkillEntity;


@Controller
public class SkillController {

    @Autowired
    private SkillService skillService;

    // 選択した月のデータを表示するメソッド
    @GetMapping(value = "/learningData/skill")
    public String displaySkill(
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "userId", required = true) Integer userId,
            Model model) {
        // サービスからデータ取得（データがない場合はダミーデータを生成）
        var skills = skillService.getSkillsByMonthAndUser(month, userId);

        // モデルにデータを設定
        model.addAttribute("skills", skills);
        model.addAttribute("selectedMonth", month);

        return "learningData/skill";
    }
}
