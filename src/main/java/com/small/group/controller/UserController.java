package com.small.group.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.small.group.dto.UserDTO;
import com.small.group.service.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/user")
@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;

	//--------------- 지우님 코드
	@GetMapping("/login")
	public String userLogin() {
		System.out.println("로그인 화면으로 이동");
		return "login";
	}
	
	
	@PostMapping("/login")
	@ResponseBody
	public boolean login(@RequestBody UserDTO userDTO) {
	  boolean idCheck = false;
	  
	  return userService.loginCheck(userDTO);
	}
		
}
