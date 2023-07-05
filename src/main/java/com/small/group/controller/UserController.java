package com.small.group.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.small.group.dto.GroupMemberDTO;
import com.small.group.dto.UserDTO;
import com.small.group.entity.User;
import com.small.group.service.GroupMemberService;
import com.small.group.service.GroupService;
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
	private final GroupService groupService;
	
	@GetMapping("/login")
	public String loginForm(Model model) {
		return "/user/loginForm";
	}
	
	@PostMapping("/login")
	@ResponseBody
	public String processLogin(@RequestBody UserDTO userDTO, 
	        HttpServletRequest request, HttpServletResponse response, Model model) {
	    User user = userService.login(userDTO);
	    
	    log.info("userDTO", userDTO.getUserId());
	    System.out.println("test: " + userDTO.getUserId());
	    System.out.println("test: " + userDTO.getPassword());
	    
	    String result = "";

	    if (user != null) {
	    	// 로그인 성공
	        HttpSession session = request.getSession();
	        session.setMaxInactiveInterval(60*60*24); // 세션 24시간 유지
	        session.setAttribute("user", user); // 세션에 사용자 정보 저장

	        // 쿠키 생성 및 전송
	        Cookie cookie = new Cookie("userId", user.getUserId());
	        cookie.setMaxAge(86400); // 쿠키 유효 시간 설정 (예: 24시간)
	        response.addCookie(cookie);
	        result = "success";
	        
	        return result; 
	    } else {
	        // 로그인 실패
	        model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
	        result = "fail";
	        return result; 
	    }
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
	    
		session = request.getSession();
	    session.invalidate();

	    // Delete the cookie
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if (cookie.getName().equals("userId")) {
	                cookie.setValue("");
	                cookie.setMaxAge(0);
	                response.addCookie(cookie);
	                break;
	            }
	        }
	    }
	    return "redirect:/";
	}
	
	@GetMapping("/register")
	public String registerForm(Model model,
							@ModelAttribute("userDTO") UserDTO userDTO,
							BindingResult bindingResult) {
		model.addAttribute("user", new User());
		return "user/registerForm";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute() @Valid UserDTO userDTO,
							BindingResult bindingResult,
							Model model) {
		if (bindingResult.hasErrors()) {
			return "user/registerForm";
		}
		userService.insertUser(userDTO);
		return "redirect:/";
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
	
	@GetMapping("/mypage")
	public void myPage(Model model,
						HttpSession session ) {
		User user = (User) session.getAttribute("user");
		Integer userNo = user.getUserNo();
		List<GroupMemberDTO> groupMemberList = groupMemberService.getGroupMemberListByUser(user); 
		
		model.addAttribute("user", user);
		model.addAttribute("groupMemberList", groupMemberList);
	}
}
