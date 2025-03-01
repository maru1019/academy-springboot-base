package com.spring.springbootapplication.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.core.Authentication;


import com.spring.springbootapplication.dto.UserLoginRequest;
import com.spring.springbootapplication.dto.UserNewAddRequest;
import com.spring.springbootapplication.dto.UserResponse;
import com.spring.springbootapplication.dao.UserMapper;
import com.spring.springbootapplication.dto.UserEditRequest;
import com.spring.springbootapplication.entity.UserEntity;
import com.spring.springbootapplication.service.CustomUserDetailsService;
import com.spring.springbootapplication.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class HomeController {

  @Autowired
  private UserService userService;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private CustomUserDetailsService customUserDetailsService; 

  private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();


  // -----Top画面表示------
  @GetMapping(value = "/user/{id}/top")
  public String displayUserTop(@PathVariable("id") Integer id, Model model) {
    UserResponse user = userService.getUserById(id); // URLのidを使ってユーザー情報を取得
    model.addAttribute("user", user); // ユーザー情報をViewに渡す
    return "user/top";
  }

  // -----ログイン機能------
  @GetMapping(value = "/user/login")
  public String displayLogin(Model model) {
    model.addAttribute("userLoginRequest", new UserLoginRequest());
    return "user/login";
  }

  // -----新規登録機能------
  @GetMapping(value = "/user/add")
  public String displayAdd(Model model) {
      model.addAttribute("userNewAddRequest", new UserNewAddRequest());
      return "user/add";
  }

  // @PostMapping("/user/add")
  // public String create(@Valid @ModelAttribute("userNewAddRequest") UserNewAddRequest userNewAddRequest, 
  //                     BindingResult result, 
  //                     Model model, 
  //                     HttpServletRequest request) {
  //     // バリデーションエラーがある場合は登録画面に戻る
  //     if (result.hasErrors()) {
  //           List<String> errorList = new ArrayList<String>();
  //            for (ObjectError error : result.getAllErrors()) {
  //                errorList.add(error.getDefaultMessage());
  //            }
  //            model.addAttribute("validationError", errorList);
  //            return "user/add";
  //     }

  //     // ユーザーをDBに保存（パスワードのハッシュ化も含む）
  //     userService.save(userNewAddRequest);

  //     // ユーザーIDを取得
  //     Integer userId = userMapper.findIdByEmail(userNewAddRequest.getEmail());
  //     if (userId == null) {
  //         throw new RuntimeException("ユーザーIDが取得できませんでした。");
  //     }

  //     // Spring Securityの認証情報を設定（自動ログイン）
  //     UserEntity userEntity = userMapper.findByEmail(userNewAddRequest.getEmail());
  //     Authentication authentication = new UsernamePasswordAuthenticationToken(userEntity, null, new ArrayList<>());
  //     SecurityContextHolder.getContext().setAuthentication(authentication);

  //     // トップページへリダイレクト
  //     return "redirect:/user/" + userId + "/top";
      
  // }

//   public String create(@Valid @ModelAttribute("userNewAddRequest") UserNewAddRequest userNewAddRequest, 
//                       BindingResult result, 
//                       Model model) {
//     if (result.hasErrors()) {
//         List<String> errorList = new ArrayList<>();
//         for (ObjectError error : result.getAllErrors()) {
//             errorList.add(error.getDefaultMessage());
//         }
//         model.addAttribute("validationError", errorList);
//         return "user/add";
//     }

//     // ユーザーをDBに保存
//     userService.save(userNewAddRequest);

//     // Spring SecurityのUserDetailsを取得
//     UserDetails userDetails = customUserDetailsService.loadUserByUsername(userNewAddRequest.getEmail());

//     // 認証情報をセット
//     Authentication authentication = 
//         new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
//     SecurityContextHolder.getContext().setAuthentication(authentication);

//     // ユーザーIDの取得
//     Integer userId = userMapper.findIdByEmail(userNewAddRequest.getEmail());
//     if (userId == null) {
//         throw new RuntimeException("ユーザーIDが取得できませんでした。");
//     }

//     return "redirect:/user/" + userId + "/top";
// }

  @PostMapping("/user/add")
  public String create(@Valid @ModelAttribute("userNewAddRequest") UserNewAddRequest userNewAddRequest, 
                        BindingResult result, 
                        Model model, 
                        HttpServletRequest request) {
      if (result.hasErrors()) {
          List<String> errorList = new ArrayList<>();
          for (ObjectError error : result.getAllErrors()) {
              errorList.add(error.getDefaultMessage());
          }
          model.addAttribute("validationError", errorList);
          return "user/add";
      }

      // ユーザーをDBに保存
      userService.save(userNewAddRequest);

      // Spring SecurityのUserDetailsを取得
      UserDetails userDetails = customUserDetailsService.loadUserByUsername(userNewAddRequest.getEmail());

      // 認証情報を作成
      Authentication authentication = 
          new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

      // セキュリティコンテキストに認証情報をセット
      SecurityContextHolder.getContext().setAuthentication(authentication);

      // セッションに認証情報を保存
      HttpSession session = request.getSession(true);
      securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, null);

      // ユーザーIDの取得
      Integer userId = userMapper.findIdByEmail(userNewAddRequest.getEmail());
      if (userId == null) {
          throw new RuntimeException("ユーザーIDが取得できませんでした。");
      }

      return "redirect:/user/" + userId + "/top";
  }



  // -----編集機能------
  @GetMapping(value = "/user/{id}/edit")
  public String displayEdit(@PathVariable Integer id, Model model) {
    UserResponse user = userService.getUserById(id); // URLのidを使ってユーザー情報を取得
    UserEditRequest userEditRequest = new UserEditRequest(); // 編集用DTOを作成
    userEditRequest.setId(user.getId());
    userEditRequest.setBiography(user.getBiography());
    userEditRequest.setData(user.getData());
    model.addAttribute("userEditRequest", userEditRequest); // 編集データを渡す
    model.addAttribute("userId", id);
    return "user/edit"; 
  }

  @PostMapping(value = "/user/{id}/edit")
  public String update(@PathVariable Integer id, @Validated @ModelAttribute UserEditRequest userEditRequest, BindingResult result, Model model) {
    if (result.hasErrors()) {
      List<String> errorList = new ArrayList<String>();
      for (ObjectError error : result.getAllErrors()) {
        errorList.add(error.getDefaultMessage());
      }
      model.addAttribute("validationError", errorList);
      return "user/edit";
    }
    
    MultipartFile imageFile = userEditRequest.getImageFile();
    if (imageFile != null && !imageFile.isEmpty()) {
        byte[] imageData = userService.convertFileToByteArray(imageFile);
        userEditRequest.setData(imageData);
    }

    // ユーザー情報を更新
    userService.update(userEditRequest);
    return "redirect:/user/" + userEditRequest.getId() + "/top";
  }
}
