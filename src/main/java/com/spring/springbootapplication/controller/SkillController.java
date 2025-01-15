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

        // 当月を取得
        int currentMonth = LocalDate.now().getMonthValue();

        // 選択された月がnullの場合、当月を選択
        if (selectedMonth == null) {
            selectedMonth = currentMonth;
        }

        // サービスから選択された月のデータを取得
        List<SkillEntity> skills = skillService.getSkillsByMonthAndUser(selectedMonth, userId);

        // 常に固定のプルダウン選択肢（当月と過去2ヶ月分）を生成
        List<Integer> dropdownMonths = List.of(
            currentMonth,
            (currentMonth - 1 + 12) % 12 == 0 ? 12 : (currentMonth - 1 + 12) % 12,
            (currentMonth - 2 + 12) % 12 == 0 ? 12 : (currentMonth - 2 + 12) % 12
        );

        // モデルに必要なデータを追加
        model.addAttribute("skills", skills); // 選択した月のデータ
        model.addAttribute("currentMonth", currentMonth); // 現在の月
        model.addAttribute("selectedMonth", selectedMonth); // 選択された月
        model.addAttribute("dropdownMonths", dropdownMonths); // プルダウンリスト用の月
        model.addAttribute("userId", userId); // ユーザーID

        return "learningData/skill"; // 対応するThymeleafテンプレート名
    }
}
