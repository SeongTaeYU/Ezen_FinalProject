package com.small.group.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.small.group.dto.BoardCategoryDTO;
import com.small.group.dto.BoardDTO;
import com.small.group.dto.GroupDTO;
import com.small.group.dto.GroupMemberDTO;
import com.small.group.dto.UserDTO;
import com.small.group.entity.Board;
import com.small.group.service.BoardCategoryService;
import com.small.group.service.BoardService;
import com.small.group.service.CommentService;
import com.small.group.service.GroupMemberService;
import com.small.group.service.GroupService;
import com.small.group.service.UserService;
import com.small.group.entity.User;

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
	private final GroupMemberService groupMemberService;
	
	// 게시글 조회
	@GetMapping("/boardList")
	public void getBoardList(Model model,
							@ModelAttribute  
							@RequestParam("groupNo") Integer groupNo,
							HttpSession session) {
		User user = (User) session.getAttribute("user");
		
		List<BoardDTO> boardList = boardService.getBoardListByGroupNo(groupNo);
		GroupDTO groupDto = groupService.readGroup(groupNo);
		
		
		// 로그인 된 회원 정보가 모임의 회원 정보가 같을 경우에 '그룹 생성' 버튼의 유무를 전달함.
		Integer isMemberIncludedGroup = groupMemberService.isMemberOfGroup(user.getUserNo(), groupNo);
		if(isMemberIncludedGroup > 0) {
			model.addAttribute("boardCreateButton", true);
		}
		model.addAttribute("boardCreateButton", false);

		model.addAttribute("groupNo", groupNo); // 추가 20230707 민혁
		model.addAttribute("group", groupDto);
		model.addAttribute("boardList", boardList);
	}
	
   // 게시판 한개 조회
   @GetMapping("/boardRead")
   public void readBoard(@RequestParam Integer boardNo, Model model, HttpSession session) {
         
       BoardDTO dto = boardService.readBoard(boardNo);
       LocalDateTime recentModDate = dto.getModDate();
         
       // 게시물 조회수 증가
       boardService.updateBoardHit(boardNo, recentModDate);
       dto = boardService.readBoard(boardNo);
       log.info("dto2.toString() : " + dto.toString());
       model.addAttribute("board", dto);
   }
	
	// 게시글 등록 폼
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
		Integer groupNo = boardDTO.getGroupNo();
		
		// 검증 오류 없으면
		boardService.insertBoard(boardDTO);
		return "redirect:/board/boardList?groupNo=" + groupNo;
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
							RedirectAttributes ra,
							Model model) {
		
		if(bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			for(FieldError error : errors) {
				System.out.println(error.getField() + "에서 유효성 검증 실패: " + error.getDefaultMessage());
			}
			dto = boardService.readBoard(dto.getBoardNo());
			ra.addFlashAttribute("board", dto);
			return "/board/boardModify";
		}
		
		BoardDTO boardDTO = boardService.readBoard(dto.getBoardNo());
		boardDTO.setBoardTitle(dto.getBoardTitle());
		boardDTO.setBoardContent(dto.getBoardContent());
		boardDTO.setBoardCategoryNo(dto.getBoardCategoryNo()); 
		boardService.updateBoard(boardDTO);
		
		return "redirect:/board/boardRead?boardNo=" + dto.getBoardNo();
	}
	
	// 게시물 삭제
	@GetMapping("/delete/{boardNo}")
	public String deleteBoard(@PathVariable Integer boardNo) {
		boolean deleted = boardService.deleteBoard(boardNo);
		return "redirect:/board/boardList";
	}
	
	// 게시물 삭제 수정
	@PostMapping("/boardDelete/{boardNo}")
	public String deleteBoard(@PathVariable("boardNo") Integer boardNo,
							Model model) {
		BoardDTO boardDTO = boardService.readBoard(boardNo);
		Integer groupNo = boardDTO.getGroupNo();
		boolean deleted = boardService.deleteBoard(boardNo);
		
		return "redirect:/board/boardList?groupNo=" + groupNo;
	}
	
}
