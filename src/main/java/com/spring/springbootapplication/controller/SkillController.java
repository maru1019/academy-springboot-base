package com.spring.springbootapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.springbootapplication.service.SkillService;
import com.spring.springbootapplication.entity.SkillEntity;

import java.time.LocalDate;
import java.util.List;

@Controller
public class SkillController {

    @Autowired
    private SkillService skillService;

    @GetMapping(value = "/learningData/{userId}/skill")
    public String displaySkills(
        @PathVariable("userId") Integer userId,
        @RequestParam(value = "createMonth", required = false) Integer selectedMonth,
        Model model) {

        // 現在の月を取得
        int currentMonth = LocalDate.now().getMonthValue();

        // 選択された月が null の場合、現在の月を代入
        if (selectedMonth == null) {
            selectedMonth = currentMonth;
        }

        // サービス層でカテゴリごとのデータを取得
        List<SkillEntity> backendSkills = skillService.getSkillsByCategoryAndMonth(1, selectedMonth, userId);
        List<SkillEntity> frontendSkills = skillService.getSkillsByCategoryAndMonth(2, selectedMonth, userId);
        List<SkillEntity> infraSkills = skillService.getSkillsByCategoryAndMonth(3, selectedMonth, userId);

        // 過去3ヶ月のプルダウン選択肢を生成
        List<Integer> dropdownMonths = List.of(
            currentMonth,
            (currentMonth - 1 + 12) % 12 == 0 ? 12 : (currentMonth - 1 + 12) % 12,
            (currentMonth - 2 + 12) % 12 == 0 ? 12 : (currentMonth - 2 + 12) % 12
        );

        // モデルにデータを追加
        model.addAttribute("backendSkills", backendSkills); // バックエンドのデータ
        model.addAttribute("frontendSkills", frontendSkills); // フロントエンドのデータ
        model.addAttribute("infraSkills", infraSkills); // インフラのデータ
        model.addAttribute("currentMonth", currentMonth); // 現在の月
        model.addAttribute("selectedMonth", selectedMonth); // 選択された月
        model.addAttribute("dropdownMonths", dropdownMonths); // プルダウンリスト用
        model.addAttribute("userId", userId); // ユーザーID

        return "learningData/skill"; // 対応するThymeleafテンプレート
    }
}
