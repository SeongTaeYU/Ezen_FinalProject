package com.small.group.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.small.group.dto.BoardCategoryDTO;
import com.small.group.dto.BoardDTO;
import com.small.group.dto.GroupDTO;
import com.small.group.dto.PageRequestDTO;
import com.small.group.dto.PageResultDTO;
import com.small.group.dto.UserDTO;
import com.small.group.entity.Board;
import com.small.group.service.*;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/board")
@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

	private final BoardService boardService;
	private final BoardCategoryService boardCategoryService;
	private final GroupService groupService;
	private final UserService userService;
	private final CommentService commentService;
	
	// 게시글 조회
	@GetMapping("/list")
	public void getList(PageRequestDTO pageRequestDTO,
						Model model) {
		PageResultDTO<BoardDTO, Board> result = boardService.getList(pageRequestDTO);
		model.addAttribute("result", result);
	}
	
	// 게시글 한개 조회
	@GetMapping("read")
	public void readBoard(@RequestParam Integer boardNo, Model model) {
		log.info("readBoard - get");
		BoardDTO dto = boardService.readBoard(boardNo);
		model.addAttribute("board", dto);
	}
	
	// 게시글 등록폼으로 이동
	@GetMapping("/register")
	public void registerForm(@ModelAttribute("boardDTO") BoardDTO boardDTO,
								BindingResult bindingResult,
								PageRequestDTO requestDTO,
								@ModelAttribute("boardCategoryDTO") BoardCategoryDTO boardCategoryDTO,
								@ModelAttribute("groupDTO") GroupDTO groupDTO,
								@ModelAttribute("userDTO") UserDTO userDTO,
								Model model) {
		log.info("boardRegister - Form");
		model.addAttribute("board", new Board());
		
		List<BoardCategoryDTO> boardCategoryList = boardCategoryService.getBoardCategoryList();
		model.addAttribute("boardCategoryList", boardCategoryList);
		
		List<GroupDTO> groupList = groupService.getGroupList();
		model.addAttribute("groupList", groupList);
		
		List<UserDTO> userList = userService.getUserList();
		model.addAttribute("userList", userList);
	}
	
	
	// 게시글 등록
	@PostMapping("/register")
	public String register(@ModelAttribute("boardDTO") @Valid BoardDTO boardDTO,
														BindingResult bindingResult,
														Model model) {
		System.out.println("test :" + boardDTO.toString());
		
		// 검증시 오류 있으면
		if (bindingResult.hasErrors()) {
			// Log field errors
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			for (FieldError error : fieldErrors) {
				log.error(error.getField() + " " + error.getDefaultMessage());
			}
			List<BoardCategoryDTO> boardCategoryList = boardCategoryService.getBoardCategoryList();
			model.addAttribute("boardCategoryList", boardCategoryList);
			
			List<GroupDTO> groupList = groupService.getGroupList();
			model.addAttribute("groupList", groupList);
			
			List<UserDTO> userList = userService.getUserList();
			model.addAttribute("userList", userList);
			
			model.addAttribute("board", boardDTO);
			return "board/register";
		}
		boardService.insertBoard(boardDTO);
		return "redirect:/board/list";
	}
	
	// 게시물 삭제
	@GetMapping("/delete/{boardNo}")
	public String deleteBoard(@PathVariable Integer boardNo) {
		boolean deleted = boardService.deleteBoard(boardNo);
		return "redirect:/board/list";
	}
	
	
}
