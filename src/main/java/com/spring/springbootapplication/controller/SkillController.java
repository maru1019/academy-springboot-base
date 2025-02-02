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
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                             @RequestParam(value = "categoryId", required = false) Integer categoryId,
                             Model model) {
        
        // `categoryId` が null または無効な ID の場合、エラーページへ
        CategoryEntity selectedCategory = categoryService.getCategoryById(categoryId);
        if (categoryId == null || (selectedCategory = categoryService.getCategoryById(categoryId)) == null) {
            model.addAttribute("errorMessage", "カテゴリが選択されていないか、見つかりません。");
            return "errorPage";
        }

        // `createMonth` のデフォルト値を現在の月に設定
        if (createMonth == null) {
            createMonth = LocalDate.now().getMonthValue();
        }

        // `SkillRequest` にデフォルト値を設定
        SkillRequest skillRequest = new SkillRequest();
        skillRequest.setCreateMonth(createMonth);
        skillRequest.setCategoryId(selectedCategory.getId());
        skillRequest.setStudyTime(0);


        // モデルにデータを渡す
        model.addAttribute("skillRequest", skillRequest);
        model.addAttribute("userId", userId);
        model.addAttribute("selectedCategory", selectedCategory);

        return "learningData/new";
    }

    @PostMapping(value = "/learningData/{userId}/new")
    public String createSkill(
        @PathVariable("userId") Integer userId,
        @Validated @ModelAttribute("skillRequest") SkillRequest skillRequest,
        BindingResult bindingResult,
        Model model,
        RedirectAttributes redirectAttributes) {

        Integer categoryId = skillRequest.getCategoryId();
        CategoryEntity selectedCategory = categoryService.getCategoryById(categoryId);

        // カテゴリが存在しない場合の処理
        if (selectedCategory == null) {
            model.addAttribute("errorMessage", "指定されたカテゴリが見つかりません。");
            model.addAttribute("selectedCategory", selectedCategory);
            model.addAttribute("skillRequest", skillRequest);
            return "learningData/new";
        }

        // サービス層で重複チェック
        if (skillService.existsByNameAndUser(skillRequest.getName(), userId, categoryId)) {
            bindingResult.rejectValue("name", "error.name", "この項目名は既に登録されています");
        }

        // バリデーションエラーまたは `selectedCategory` が `null` の場合
        if (bindingResult.hasErrors() || selectedCategory == null) {
            model.addAttribute("selectedCategory", selectedCategory);
            model.addAttribute("skillRequest", skillRequest);
            return "learningData/new";
        }

        try {
            skillService.save(userId, skillRequest);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "データの保存に失敗しました: " + e.getMessage());
            model.addAttribute("selectedCategory", selectedCategory);
            model.addAttribute("skillRequest", skillRequest);
            return "learningData/new";
        }

        redirectAttributes.addFlashAttribute("isSaved", true);
        return "redirect:/learningData/" + userId + "/new";
    }

}
