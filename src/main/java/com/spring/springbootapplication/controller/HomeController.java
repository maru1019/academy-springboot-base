package com.spring.springbootapplication.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.spring.springbootapplication.dto.UserLoginRequest;
import com.spring.springbootapplication.dto.UserNewAddRequest;
import com.spring.springbootapplication.entity.UserEntity;
import com.spring.springbootapplication.service.UserService;

@Controller
public class HomeController {

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @GetMapping(value = "/user/top")
    public String displayTop() {
      return "top"; // `top.html` を返す
  }

  // -----ログイン機能------
  @GetMapping(value = "/user/login")
    public String displayLogin() {
      return "user/login";
    }

  @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public String login(@Validated @ModelAttribute UserLoginRequest userLoginRequest, BindingResult result, Model model) {
      if (result.hasErrors()) {
        List<String> errorList = new ArrayList<String>();
        for (ObjectError error : result.getAllErrors()) {
            errorList.add(error.getDefaultMessage());
        }
        model.addAttribute("validationError", errorList);
        return "user/login"; 
      }

      UserEntity user = userService.getUserByEmail(userLoginRequest.getEmail());
      if (user == null || !passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
          model.addAttribute("error", "メールアドレスまたはパスワードが一致しません。");
          return "user/login";
      }
    
      return "user/top";
    }


  // -----新規登録機能------
  @GetMapping(value = "/user/add")
    public String displayAdd(Model model) {
        model.addAttribute("userNewAddRequest", new UserNewAddRequest());
        return "user/add";
    }

  @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public String create(@Validated @ModelAttribute UserNewAddRequest userNewAddRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            return "user/add";
        }
       
        userService.save(userNewAddRequest);
        return "user/top";
    }
}
