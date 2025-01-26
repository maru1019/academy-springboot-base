package com.spring.springbootapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.spring.springbootapplication.service.SkillService;
import com.spring.springbootapplication.dto.SkillRequest;
import com.spring.springbootapplication.entity.SkillEntity;
import com.spring.springbootapplication.enums.Category;
import com.spring.springbootapplication.entity.CategoryEntity;
import com.spring.springbootapplication.service.CategoryService;


import java.time.LocalDate;
import java.util.List;

import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Controller
public class SkillController {

    @Autowired
    private SkillService skillService;

    @Autowired
    private CategoryService categoryService;

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
        List<SkillEntity> backendSkills = skillService.getSkillsByCategoryAndMonth(Category.BACKEND.getId(), selectedMonth, userId);
        List<SkillEntity> frontendSkills = skillService.getSkillsByCategoryAndMonth(Category.FRONTEND.getId(), selectedMonth, userId);
        List<SkillEntity> infraSkills = skillService.getSkillsByCategoryAndMonth(Category.INFRA.getId(), selectedMonth, userId);

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

        return "learningData/skill"; 
    }

    @GetMapping(value = "/learningData/{userId}/new")
    public String displayAdd(@PathVariable("userId") Integer userId, 
                             @RequestParam(value = "createMonth", required = false) Integer createMonth, 
                             Model model) {

    // カテゴリ情報を取得
    List<CategoryEntity> categories = categoryService.getCategoriesByUserId(userId);
    
    if (categories.isEmpty()) {
        model.addAttribute("errorMessage", "指定されたユーザーに対応するカテゴリが見つかりません。");
        return "errorPage"; // エラーページにリダイレクト
    }

    // 選択された月が null の場合は現在の月を設定
    if (createMonth == null) {
        createMonth = LocalDate.now().getMonthValue();
    }

    SkillRequest skillRequest =  new SkillRequest();
    skillRequest.setCreateMonth(createMonth);

    // サービスから学習項目を取得
    List<SkillEntity> skills = skillService.getSkillsByMonthAndUser(createMonth, userId);

    // モデルに必要なデータを渡す
    model.addAttribute("categories", categories);
    model.addAttribute("skillRequest", skillRequest);
    model.addAttribute("skills", skills); // 学習項目
    model.addAttribute("userId", userId); // ユーザーID

    return "learningData/new";
    }


    // @PostMapping(value = "/learningData/{userId}/new")
    // public String createSkill(
    //     @PathVariable("userId") Integer userId,
    //     @ModelAttribute("skillRequest") @Valid SkillRequest skillRequest,
    //     BindingResult bindingResult,
    //     Model model) {

    //     // バリデーションエラーがある場合は入力画面に戻す
    //     if (bindingResult.hasErrors()) {
    //         CategoryEntity category = categoryService.getCategoryByUserId(userId);
    //         model.addAttribute("categoryName", category.getName());
    //         return "learningData/new";
    //     }

    //     // ユーザーIDをセット
    //     skillRequest.setUserId(userId);

    //     // サービス層でスキルを登録
    //     skillService.save(skillRequest);
    //     return "redirect:/learningData/{userId}/skill";
    // }

    
}
