package com.small.group.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.small.group.service.*;

import lombok.RequiredArgsConstructor;

@RequestMapping("/board")
@Controller
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	private final BoardCategoryService boardCategoryService;
	private final CommentService commentService;
}
