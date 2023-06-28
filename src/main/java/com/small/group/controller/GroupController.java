package com.small.group.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.small.group.service.*;

import lombok.RequiredArgsConstructor;

@RequestMapping("/group")
@Controller
@RequiredArgsConstructor
public class GroupController {
	
	private final GroupService groupService;
	private final GroupCategoryService groupCategoryService;
	private final GroupMemberService groupMemberService;
	private final ChatService chatService;

}
