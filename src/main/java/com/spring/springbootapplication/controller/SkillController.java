package com.spring.springbootapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.springbootapplication.dto.SkillRequest;
import com.spring.springbootapplication.service.SkillService;
import com.spring.springbootapplication.entity.SkillEntity;

@Controller
public class SkillController {

  @Autowired
  private SkillService skillService;

  // スキル画面表示
  // @GetMapping(value = "/learningData/{id}/skill")
  @GetMapping(value = "/learningData/skill")
  public String displaySkill() {
  // public String displaySkill(@PathVariable Integer id, Model model) {
    // SkillEntity skillEntity = skillService.getSkillById(id);
    // SkillRequest skill = skillService.converToSkillRequest(skillEntity);
    // model.addAttribute("skill", skill);
    return "learningData/skill";
  }
  
}
