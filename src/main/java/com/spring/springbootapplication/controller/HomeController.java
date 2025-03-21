package com.spring.springbootapplication.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


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
  public String displayUserTop(@PathVariable("id") Integer id, Model model, Authentication authentication) {

    // 認証されたユーザーのメールアドレスを取得
    String email = authentication.getName();

    // 現在ログイン中のユーザー情報を取得し、ユーザーIDを取得
    UserEntity currentUser = userService.getUserByEmail(email);
    Integer currentUserId = currentUser.getId();

    // URLのIDとログインユーザーのIDが違う場合はログインページへリダイレクト
    if (!id.equals(currentUserId)) {
        return "redirect:/user/login";
    }

    UserResponse user = userService.getUserById(id); // URLのidを使ってユーザー情報を取得
    model.addAttribute("user", user); // ユーザー情報をViewに渡す
    model.addAttribute("name", user.getName());
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
  public String displayEdit(@PathVariable Integer id, Model model, Authentication authentication) {

    // 認証されたユーザーのメールアドレスを取得
    String email = authentication.getName();

    // 現在ログイン中のユーザー情報を取得し、ユーザーIDを取得
    UserEntity currentUser = userService.getUserByEmail(email);
    Integer currentUserId = currentUser.getId();

    // URLのIDとログインユーザーのIDが違う場合はログインページへリダイレクト
    if (!id.equals(currentUserId)) {
        return "redirect:/user/login";
    }

    UserResponse user = userService.getUserById(id); // URLのidを使ってユーザー情報を取得
    UserEditRequest userEditRequest = new UserEditRequest(); // 編集用DTOを作成
    userEditRequest.setId(user.getId());
    userEditRequest.setBiography(user.getBiography());
    userEditRequest.setData(user.getData());

    // 画像のURLを設定
    if (user.getData() != null) {
      userEditRequest.setImageUrl("/user/" + user.getId() + "/avatar"); // アップロードされた画像
    } else if (user.getImageUrl() != null && !user.getImageUrl().isEmpty()) {
        userEditRequest.setImageUrl(user.getImageUrl()); // 以前の画像を保持
    } else {
        userEditRequest.setImageUrl("/images/image.png"); // デフォルト画像
    }

    model.addAttribute("userEditRequest", userEditRequest); // 編集データを渡す
    model.addAttribute("userId", id);
    return "user/edit"; 
  }

  @GetMapping("/user/{id}/avatar")
  @ResponseBody
  public ResponseEntity<byte[]> getUserAvatar(@PathVariable Integer id) {
      UserResponse user = userService.getUserById(id);
      
      if (user.getData() == null) {
          System.out.println("【ERROR】ユーザーID " + id + " の画像データが NULL になっています");
          return ResponseEntity.notFound().build();
      }
  
      System.out.println("【INFO】ユーザーID " + id + " の画像データが取得されました。データサイズ: " + user.getData().length);
  
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.IMAGE_JPEG); // JPEG の場合
      return new ResponseEntity<>(user.getData(), headers, HttpStatus.OK);
  }
  

  @PostMapping(value = "/user/{id}/edit")
  public String update(@PathVariable Integer id, @Validated @ModelAttribute UserEditRequest userEditRequest,
                      BindingResult result, Model model,
                      @RequestParam(name = "existingImageUrl", required = false) String existingImageUrl) {
      
      if (result.hasErrors()) {
          List<String> errorList = new ArrayList<>();
          for (ObjectError error : result.getAllErrors()) {
              errorList.add(error.getDefaultMessage());
          }
          model.addAttribute("validationError", errorList);
          return "user/edit";
      }
      
      MultipartFile imageFile = userEditRequest.getImageFile();
      if (imageFile != null && !imageFile.isEmpty()) {
          userEditRequest.setData(userService.convertFileToByteArray(imageFile));
      } else {
          UserResponse existingUser = userService.getUserById(id);
          if (existingUser.getData() != null) {
              userEditRequest.setData(existingUser.getData()); // 以前の画像データを維持
          }
      }
  
      userService.update(userEditRequest);
      return "redirect:/user/" + userEditRequest.getId() + "/top";
  }
  
}
