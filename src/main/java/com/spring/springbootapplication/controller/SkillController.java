package com.spring.springbootapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.spring.springbootapplication.service.SkillService;
import com.spring.springbootapplication.service.CategoryService;
import com.spring.springbootapplication.service.UserService;
import com.spring.springbootapplication.dto.SkillRequest;
import com.spring.springbootapplication.entity.SkillEntity;
import com.spring.springbootapplication.entity.UserEntity;
import com.spring.springbootapplication.enums.Category;
import com.spring.springbootapplication.entity.CategoryEntity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Map;
import java.util.HashMap;


@Controller
public class SkillController {

    @Autowired
    private SkillService skillService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;


    // スキル一覧表示
    @GetMapping(value = "/learningData/{userId}/skill")
    public String displaySkills(
        @PathVariable("userId") Integer userId,
        @RequestParam(value = "createMonth", required = false) Integer selectedMonth,
        Model model,
        Authentication authentication) {

        // 認証されたユーザーのメールアドレスを取得
        String email = authentication.getName();

        // 現在ログイン中のユーザー情報を取得
        UserEntity currentUser = userService.getUserByEmail(email);
        Integer currentUserId = currentUser.getId();

        // URLのuserIdと現在ログイン中のユーザーIDを比較
        if (!userId.equals(currentUserId)) {
            // 不正アクセスの場合はログイン画面にリダイレクト
            return "redirect:/user/login";
        }

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

        SkillRequest skillRequest = new SkillRequest();

        if (!backendSkills.isEmpty()) {
            skillRequest.setStudyTime(backendSkills.get(0).getStudyTime());
        } else if (!frontendSkills.isEmpty()) {
            skillRequest.setStudyTime(frontendSkills.get(0).getStudyTime());
        } else if (!infraSkills.isEmpty()) {
            skillRequest.setStudyTime(infraSkills.get(0).getStudyTime());
        }

        // モデルにデータを追加
        model.addAttribute("backendSkills", backendSkills); // バックエンドのデータ
        model.addAttribute("frontendSkills", frontendSkills); // フロントエンドのデータ
        model.addAttribute("infraSkills", infraSkills); // インフラのデータ
        model.addAttribute("currentMonth", currentMonth); // 現在の月
        model.addAttribute("selectedMonth", selectedMonth); // 選択された月
        model.addAttribute("dropdownMonths", dropdownMonths); // プルダウンリスト用
        model.addAttribute("userId", userId); // ユーザーID
        model.addAttribute("skillRequest", skillRequest);

        return "learningData/skill"; 
    }

    // 新規追加画面表示
    @GetMapping(value = "/learningData/{userId}/new")
    public String displayAdd(@PathVariable("userId") Integer userId, 
                         @RequestParam(value = "createMonth", required = false) Integer createMonth,
                         @RequestParam(value = "categoryId", required = false) Integer categoryId,
                         Model model,
                         Authentication authentication) {
                            
        // 認証されたユーザーのメールアドレスを取得
        String email = authentication.getName();

        // 現在ログイン中のユーザー情報を取得
        UserEntity currentUser = userService.getUserByEmail(email);
        Integer currentUserId = currentUser.getId();

        // URLのuserIdと現在ログイン中のユーザーIDを比較
        if (!userId.equals(currentUserId)) {
            // 不正アクセスの場合はログイン画面にリダイレクト
            return "redirect:/user/login";
        }

        // `categoryId` が null の場合、エラーメッセージを表示して戻る
        if (categoryId == null) {
            model.addAttribute("errorMessage", "カテゴリが選択されていません。");
            model.addAttribute("selectedCategory", new CategoryEntity());
            model.addAttribute("skillRequest", new SkillRequest());
            model.addAttribute("userId", userId);
            return "learningData/new"; 
        }

        // カテゴリが存在しない場合の処理
        CategoryEntity selectedCategory = categoryService.getCategoryById(categoryId);
        if (selectedCategory == null) {
            model.addAttribute("errorMessage", "指定されたカテゴリが見つかりません。");
            model.addAttribute("selectedCategory", new CategoryEntity());
            model.addAttribute("skillRequest", new SkillRequest());
            model.addAttribute("userId", userId);
            return "learningData/new"; 
        }

        // `SkillRequest` にデフォルト値を設定
        SkillRequest skillRequest = new SkillRequest();
        skillRequest.setCategoryId(selectedCategory.getId());

        // モデルにデータを渡す
        model.addAttribute("skillRequest", skillRequest);
        model.addAttribute("userId", userId);
        model.addAttribute("selectedCategory", selectedCategory);
        model.addAttribute("selectedMonth", createMonth);
        
        return "learningData/new";
    }


    // 新規追加
    @PostMapping(value = "/learningData/{userId}/new", produces = "application/json")
    @ResponseBody
    public Map<String, Object> createSkill(
        @PathVariable("userId") Integer userId,
        @Validated @ModelAttribute("skillRequest") SkillRequest skillRequest,
        BindingResult bindingResult) {
    
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        // 1. フォームバリデーションのエラーを収集
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
        }
        
        // 2. 重複チェックも追加
        if (skillService.existsByNameAndUserAndMonth(skillRequest.getName(), userId, skillRequest.getCreateMonth())) {
            String duplicateErrorMessage = String.format("%s は既に登録されています", skillRequest.getName());
            errors.put("name", duplicateErrorMessage);  // 項目名のエラーとして追加
        }
        
        // 3. すべてのエラーを返す
        if (!errors.isEmpty()) {
            response.put("errors", errors);
            return response;
        }
        
        // 4. エラーがなければ保存処理
        try {
            skillService.save(userId, skillRequest);
            response.put("success", true);
        } catch (Exception e) {
            response.put("errors", Map.of("general", "データの保存に失敗しました: " + e.getMessage()));
        }
        
        return response;
        
    }

    // 学習時間編集
    @PostMapping(value = "/learningData/{userId}/edit", produces = "application/json")
    @ResponseBody
    public Map<String, Object> updateSkill(@PathVariable("userId") Integer userId,
                        @ModelAttribute SkillRequest skillRequest,
                        BindingResult bindingResult ) {

        Map<String, Object> response = new HashMap<>();

        if(skillRequest.getStudyTime() < 0) {
            response.put("succes", false);
            return response;
        }

        try {
            skillService.update(userId, skillRequest);

            SkillEntity updatedSkill = skillService.getSkillById(skillRequest.getId());

            response.put("success", true);
            response.put("updatedStudyTime", updatedSkill.getStudyTime()); //  更新後の学習時間を返す

        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }

        return response;
    }

    // 項目名削除
    @PostMapping(value = "/learningData/{userId}/delete", produces = "application/json")
    @ResponseBody
    public Map<String, Object> deleteSkill(@PathVariable("userId") Integer userId,
                        @ModelAttribute SkillRequest skillRequest) {

        Map<String, Object> response = new HashMap<>();

        try {
            skillService.delete(userId, skillRequest);
            response.put("success",true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }

        return response;
    }

    @GetMapping(value = "/learningData/{userId}/chart", produces = "application/json")
    @ResponseBody
    public Map<String, Object> getChartData(@PathVariable("userId") Integer userId) {

        Map<String, Object> response = new HashMap<>();

        // 現在の月を取得
        int currentMonth = LocalDate.now().getMonthValue();
        List<Integer> recentMonths = List.of(
            currentMonth,
            (currentMonth - 1 + 12) % 12 == 0 ? 12 : (currentMonth - 1 + 12) % 12,
            (currentMonth - 2 + 12) % 12 == 0 ? 12 : (currentMonth - 2 + 12) % 12
        );

        List<Integer> months = skillService.getRecentMonths();

        // 各月ごとのカテゴリ別学習時間合計を取得
        Map<String, Map<Integer, Integer>> categoryData = new HashMap<>();
        categoryData.put("backend", skillService.getTotalStudyTimeByCategory(Category.BACKEND.getId(), userId, months));
        categoryData.put("frontend", skillService.getTotalStudyTimeByCategory(Category.FRONTEND.getId(), userId, months));
        categoryData.put("infra", skillService.getTotalStudyTimeByCategory(Category.INFRA.getId(), userId, months));

        response.put("labels", months);
        response.put("categoryData", categoryData);
        
        return response;
    }
}