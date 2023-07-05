package com.small.group.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.ResponseEntity;
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
	@GetMapping("/boardList")
	public void getBoardList(PageRequestDTO pageRequestDTO,
							Model model,
							@RequestParam("groupNo") Integer groupNo) {
		PageResultDTO<BoardDTO, Board> result = boardService.getBoardList(pageRequestDTO);
		List<BoardDTO> groupBoardList = result.getDtoList().stream().filter(dto -> (dto.getGroupNo() == groupNo)).collect(Collectors.toList());
		result.setDtoList(groupBoardList);
		model.addAttribute("result", result);
	}


	
	// 게시글 한개 조회
	@GetMapping("/boardRead")
	public void readBoard(@RequestParam Integer boardNo, Model model) {
		log.info("readBoard - Get");
		BoardDTO dto = boardService.readBoard(boardNo);
		boardService.updateBoardHit(boardNo);
		model.addAttribute("board", dto);
	}
	
	// 게시글 등록폼으로 이동
	@GetMapping("/boardRegister")
	public void registerBoard(@ModelAttribute("boardDTO") BoardDTO boardDTO,
						@ModelAttribute("boardCategoryDTO") BoardCategoryDTO boardCategoryDTO,
						@ModelAttribute("groupDTO") GroupDTO groupDTO,
						@ModelAttribute("userDTO") UserDTO userDTO,
						BindingResult bindingResult,
						Model model) {
		log.info("@@@@@@@@@@@boardRegister Form");
		model.addAttribute("board", new Board());
		
		List<BoardCategoryDTO> boardCategoryList = boardCategoryService.getBoardCategoryList();
		model.addAttribute("boardCategoryList", boardCategoryList);
		
		List<GroupDTO> groupList = groupService.getGroupList();
		model.addAttribute("groupList", groupList);
		
		List<UserDTO> userList = userService.getUserList();
		model.addAttribute("userList", userList);
	}
	
	
	// 게시글 등록
	@PostMapping("/boardRegister")
	public String registerBoard(@ModelAttribute("boardDTO") @Valid BoardDTO boardDTO,
							BindingResult bindingResult,
							Model model) {
		System.out.println("test: " + boardDTO.toString());
		log.info("register process group.toString: " + boardDTO.toString());
		
		// 검증시 오류 있으면
		if(bindingResult.hasErrors()) {

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
			log.info("boardRegister");
			
			return "board/boardRegister";
		}
		
		// 검증 오류 없으면
		boardService.insertBoard(boardDTO);
		return "redirect:/board/boardList";
	}
	
	// 게시글 수정 폼
	@GetMapping("/boardModify")
	public void updateBoardForm(@RequestParam Integer boardNo,
								@ModelAttribute("boardDTO") BoardDTO boardDTO,
								BindingResult bindingResult,
								Model model) {
		log.info("boardModify Form");
		
		BoardDTO dto = boardService.readBoard(boardNo);
		System.out.println("dto.toString: " + dto.toString());
		model.addAttribute("board", dto);
		
		List<BoardCategoryDTO> boardCategoryList = boardCategoryService.getBoardCategoryList();
		model.addAttribute("boardCategoryList", boardCategoryList);
		
		List<GroupDTO> groupList = groupService.getGroupList();
		model.addAttribute("groupList", groupList);
		
		List<UserDTO> userList = userService.getUserList();
		model.addAttribute("userList", userList);
		
	}
	
	// 게시글 수정
	@PostMapping("/boardModify")
	public String updateBoard(@ModelAttribute @Valid BoardDTO dto,
							BindingResult bindingResult,
							Model model) {
		log.info("boardModify - post dto: " + dto.toString());
		System.out.println("boardTitle: " + dto.getBoardTitle());
		System.out.println("boardContent: " + dto.getBoardContent());
		
		// 검증시 오류 있으면
				if (bindingResult.hasErrors()) {
				// Log field errors
				List<FieldError> fieldErrors = bindingResult.getFieldErrors();
				for (FieldError error : fieldErrors) {
					System.out.println("####" + error.getDefaultMessage() + ": " + error.getField());
					log.error(error.getField() + " " + error.getDefaultMessage());
				}
				BoardDTO boardDTO = boardService.readBoard(dto.getBoardNo());
				
				model.addAttribute("board", dto);
				
				List<BoardCategoryDTO> boardCategoryList = boardCategoryService.getBoardCategoryList();
				model.addAttribute("boardCategoryList", boardCategoryList);
				
				List<GroupDTO> groupList = groupService.getGroupList();
				model.addAttribute("groupList", groupList);
				
				List<UserDTO> userList = userService.getUserList();
				model.addAttribute("userList", userList);
				return "board/boardModify";
		}
			boardService.updateBoard(dto);
			return "redirect:/board/boardRead?boardNo=" + dto.getBoardNo();
	}
	
	// 게시물 삭제
	@GetMapping("/delete/{boardNo}")
	public String deleteBoard(@PathVariable Integer boardNo) {
		boolean deleted = boardService.deleteBoard(boardNo);
		return "redirect:/board/boardList";
	}
	
}
