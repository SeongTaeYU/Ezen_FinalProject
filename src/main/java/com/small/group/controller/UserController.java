package com.small.group.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.small.group.dto.GroupMemberDTO;
import com.small.group.dto.PageRequestDTO;
import com.small.group.dto.PageResultDTO;
import com.small.group.dto.UserDTO;
import com.small.group.entity.User;
import com.small.group.service.GroupMemberService;
import com.small.group.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/user")
@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	private final GroupMemberService groupMemberService;

	@GetMapping("/welcome")
	public String welcome(Model model) {
		
		return "welcome";
	}
	
	@GetMapping("/list")
	public void getList(PageRequestDTO pageRequestDTO, Model model) {
		PageResultDTO<UserDTO, User> result = userService.getList(pageRequestDTO);
		model.addAttribute("result", result);
	}
	
	@GetMapping("/read")
	public void readUser(@RequestParam Integer userNo, Model model) {
		log.info("@@@@@@회원상세정보 메소드 get");
		UserDTO dto = userService.readUser(userNo);
		GroupMemberDTO groupMember = groupMemberService.readGroupMember(userNo);
		model.addAttribute("user", dto);
		model.addAttribute("group", groupMember);
	}
	
	@GetMapping("modify")
	public void modify(@RequestParam("userNo") Integer userNo,
						@ModelAttribute("userDTO") UserDTO userDTO,
						BindingResult bindingResult,
						Model model) {
		log.info("@@@@@@@@회원 수정 get");
		UserDTO dto = userService.readUser(userNo);
		model.addAttribute("user", dto);
	}
	
	@PostMapping("/modify")
	public String modify(@ModelAttribute @Valid UserDTO userDTO,
						BindingResult bindingResult,
						Model model) {
		log.info("@@@@@@@회원 수정 post");
		
		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			for (FieldError error : fieldErrors) {
				log.error(error.getField() + " " + error.getDefaultMessage());
			}
			model.addAttribute("user", userDTO);
			System.out.println("@@@@@@@회원 수정 post2" + userDTO);
			return "user/modify";
		}
		userService.updateUser(userDTO);
		return "redirect:/user/list";
	}
	
	@GetMapping("/delete/{userNo}")
	public String deleteUser(@PathVariable Integer userNo) {
		boolean deleted = userService.deleteUser(userNo);
		return "redirect:/user/list";
	}
	
	@GetMapping("/register")
	public String userRegisterForm(Model model, 
					@ModelAttribute("userDTO") UserDTO userDTO, 
					BindingResult bindingResult) {
		log.info("@@@@회원가입 메소드 get");
		model.addAttribute("user", new User());
		return "register";
	}
	
	@GetMapping("/login")
	public String userLogin() {
		log.info("@@@@로그인 메소드 get");
		return "login";
	}
	
	
	@PostMapping("/login")
	@ResponseBody
	public boolean login(@RequestBody UserDTO userDTO) {
	  log.info("@@@@로그인 메소드 POST");
	  System.out.println("userDTO :" + userDTO);
	  
	  return userService.loginCheck(userDTO);
	  
	}
			
	@PostMapping("/idcheck")
	@ResponseBody
	public boolean idExist(Model model, 
					@ModelAttribute("userId") String userId, 
					BindingResult bindingResult) {
		
		log.info("idcheck 메소드 get@@@");
		System.out.println("!!!!!!" + userId);

		System.out.println("출력되나 : " + userService.idExist(userId));
		
		return userService.idExist(userId);
	}
	
	
	@PostMapping("/register")
	public String register(@ModelAttribute("userDTO") UserDTO user,
							BindingResult bindingResult,
							Model model) {
		log.info("@@@@회원가입 메소드 post 시작 ");

		userService.register(user);
		
		return "home";
	}
		
}
