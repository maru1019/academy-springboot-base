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

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
public class SkillController {

    @Autowired
    private SkillService skillService;

    /**
     * 初期表示用（当月のデータを取得）
     */
    @GetMapping(value = "/learningData/{userId}/skill")
    public String displayInitialSkill(
            @PathVariable("userId") Integer userId, 
            @RequestParam(value = "createMonth", required = false) Integer createMonth,
            Model model) {

        if (createMonth == null) {
            createMonth = LocalDate.now().getMonthValue();
        }

        // サービスから当月データを取得
        List<SkillEntity> skills = skillService.getSkillsByMonthAndUser(createMonth, userId);
        int currentMonth = createMonth;

        // 過去3ヶ月分の月を計算
        List<Integer> availableMonths = IntStream.rangeClosed(0, 2)
        .mapToObj(i -> {
            // (createMonth - i + 12) % 12 == 0 ? 12 : (createMonth - i + 12) % 12 の計算を行う
            int month = (currentMonth - i + 12) % 12;
            return month == 0 ? 12 : month;
        })
        .collect(Collectors.toList());


        // モデルにデータを設定
        model.addAttribute("skills", skills);
        model.addAttribute("selectedMonth", createMonth);
        model.addAttribute("availableMonths", availableMonths);
        model.addAttribute("userId", userId);

        return "learningData/skill";
    }

    /**
     * プルダウン選択後のデータ取得
     */
    @PostMapping(value = "/learningData/{userId}/skill")
    public String displaySkill(
            @PathVariable("userId") Integer userId,
            @RequestParam("createMonth") Integer createMonth,
            Model model) {
        // サービスから選択した月のデータを取得
        List<SkillEntity> skills = skillService.getSkillsByMonthAndUser(createMonth, userId);


    
        // モデルにデータを設定
        model.addAttribute("skills", skills);
        model.addAttribute("selectedMonth", createMonth);
        model.addAttribute("userId", userId);

        return "learningData/skill";
    }
}

