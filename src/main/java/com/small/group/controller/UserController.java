package com.small.group.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.small.group.service.*;

import lombok.RequiredArgsConstructor;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;

}
