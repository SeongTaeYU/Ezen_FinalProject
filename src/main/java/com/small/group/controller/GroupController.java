package com.small.group.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
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

import com.small.group.entity.*;
import com.small.group.repository.GroupRepository;
import com.small.group.dto.*;
import com.small.group.service.*;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/group")
@Controller
@RequiredArgsConstructor
@Slf4j
public class GroupController {
	
	private final GroupService groupService;
	private final GroupCategoryService groupCategoryService;
	private final GroupMemberService groupMemberService;
	private final ChatService chatService;
	private final RegionService regionService;
	private final UserService userService;
	private final GroupRepository groupRepository;
	private final BoardCategoryService boardCategoryService;
	
	
	@GetMapping("/groupList")
	public String getGroupList(PageRequestDTO pageRequestDTO,
	                           @RequestParam(required = false) Integer groupCategoryNo,
	                           @RequestParam(required = false) String searchKeyword,
	                           Model model, HttpSession session) {
	    System.out.println("!@@@@@@@ groupCategoryNo : " + groupCategoryNo);
	    
	    
	    User user = (User) session.getAttribute("user");
	    if (user == null) {
	    	return "redirect:/user/login";
	    }
	    
	    if (searchKeyword != null) {
	    	List<GroupDTO> groupList = groupService.getGroupList(searchKeyword);
	    	
	    	model.addAttribute("groupList", groupList);
	    	model.addAttribute("searchKeyword",searchKeyword);
	    	
	    	return "group/groupSearch";
	    }
	    
	    GroupCategoryDTO groupCategoryDTO = null;
	    
	    if (groupCategoryNo != null) {
	    	pageRequestDTO.setGroupCategoryNo(groupCategoryNo);
	    	groupCategoryDTO = groupCategoryService.readGroupCategory(groupCategoryNo);
	    } else {
	        // groupCategoryNo가 없을 경우 전체 그룹을 가져오도록 설정
	        pageRequestDTO.setGroupCategoryNo(null);
	    }

	    PageResultDTO<GroupDTO, Group> result = groupService.getGroupList(pageRequestDTO);
	    
	    List<GroupDTO> filteredGroups;
	    
		if (groupCategoryNo != null) {
			filteredGroups = result.getDtoList().stream()
				.filter(group -> group.getGroupCategoryNo() == groupCategoryNo)
				.collect(Collectors.toList());
		} else {
			filteredGroups = result.getDtoList();
		}
		
		log.info("groupCategory dto.toString() : " + groupCategoryDTO);
		
	    model.addAttribute("result", result);
	    model.addAttribute("filteredGroups", filteredGroups);
	    model.addAttribute("groupCategoryNo", groupCategoryNo);
	    model.addAttribute("groupName", searchKeyword);
	    model.addAttribute("groupCategory", groupCategoryDTO);
	    
	    
	    return "group/groupList";
	}
	

    // 모임 등록창으로 이동
	@GetMapping("/groupInsert")
    public String insertGroup(Model model, 
    		@ModelAttribute("groupDTO") GroupDTO groupDTO,
    		BindingResult bindingResult, HttpSession session) {
    	log.info("groupInsertForm");
    	
    	// 회원이 세션에 존재하지 않을 경우 '로그인 화면'으로 이동함
    	User user = (User) session.getAttribute("user");
	    if (user == null) {
	    	return "redirect:/user/login";
	    }
    	
    	List<GroupCategoryDTO> groupCategoryList = groupCategoryService.getGroupCategoryList();
    	model.addAttribute("groupCategoryList", groupCategoryList);
    	List<RegionDTO> regionList = regionService.getRegionList();
    	model.addAttribute("regionList", regionList);
    	return "group/groupInsert";
    }

    /*
     * 그룹(모임) 등록처리(Post 요청)
     *  - GroupDTO 파라미터 
     *    : 등록 정보를 모두 담아주는 커맨트 객체
     *    : 등록하다 오류나면 입력정보를 그대로 갖고 다시 등록폼으로 가서
     *      입력했던 정보 그대로 세팅할 때 커맨드 객체가 필요함. 
     * @Valid : 뒤에 오는 커맨드 객체에 설정된 조건에 합당한지 검증
     *  - 검증후 문제가 있으면 다시 입력폼으로 이동     
     * BindingResult 
	 *  - BindingResult 객체는 검증 결과에 대한 결과 정보들을 담고 있습니다
	 *  - 검증 객체 바로 뒤어 오도록 한다.
	 * RedirectAttributes
	 *  - VO 입력값 전송
	 *  - 오류값 객체 전송  
     */
	@PostMapping("/groupInsert")
    public String insertGroup(@ModelAttribute("groupDTO") GroupDTO groupDTO, 
							BindingResult bindingResult, 
							HttpServletRequest request,
							Model model) {
    	
    	log.info("register process group.toString : "+ groupDTO.toString());

        // 검증시 오류 있으면
        if (bindingResult.hasErrors()) {
            // Log field errors
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors) {
                log.error( error.getField() + " "+ error.getDefaultMessage());
            }
            List<GroupCategoryDTO> groupCategoryList = groupCategoryService.getGroupCategoryList();
        	model.addAttribute("groupCategoryList", groupCategoryList);
        	List<RegionDTO> regionList = regionService.getRegionList();
        	model.addAttribute("regionList", regionList);
        	List<UserDTO> userList = userService.getUserList();
        	model.addAttribute("userList", userList);
            model.addAttribute("group", groupDTO);
            log.info("groupInsert");
            
            return "group/groupInsert";
        }
        // 검증 오류 없음
        HttpSession session = request.getSession();
        User userEntity = (User)session.getAttribute("user");
        System.out.println("userNoOO: " + groupDTO.getUserNo());
        Group groupEntity = groupService.insertGroup(groupDTO); 
        GroupMember groupMemberEntity = GroupMember.builder()
                       .group(groupEntity)
                       .user(userEntity)
                       .build();
        
        GroupMemberDTO groupMemberDTO = groupMemberService.entityToDto(groupMemberEntity);
        groupMemberService.insertGroupMember(groupMemberDTO);
        
        // 20230707 23:55 현재 테스트중 
        return "redirect:/group/groupList";
    }

 // 그룹 한 개 조회
	@GetMapping("/groupRead")
    public String readGroup(@RequestParam Integer groupNo, Model model,
                              @ModelAttribute("groupDTO") GroupDTO groupDTO,
                              HttpSession session) {

            // 세션에서 사용자 정보 가져오기
            User user = (User) session.getAttribute("user");
            if (user == null) {
            	return "redirect:/user/login";
            	} 
            // 세션에 사용자 정보(userNo) 저장할 변수
            Integer userNo = 0;
            // 사용자가 가입가능한 DB인지 확인하는 변수
            Integer isMemberResult = 0;
            
            //사용자의 정보가 null이 아니라면 가입 가능한지 쿼리로 확인
            if (user != null) {

                userNo = user.getUserNo(); // userNo를 user.getUserNo()로 설정
                
                isMemberResult = groupMemberService.isMemberOfGroup(userNo, groupNo);
                
                //가입된 모임이 없다면 0
                if (isMemberResult == 0) {
                    userNo = user.getUserNo();
                } else {
                    // 가입된 모임이 있는 경우 userNo를 0으로 설정
                    userNo = 0;
                    
                }
            } else {
            	//사용자 정보가 없다면 0 할당
                userNo = 0;
            }
            

            System.out.println("유저번호 : " + userNo);
            System.out.println("그룹번호 : " + groupNo);
            System.out.println("가입된 그룹인지 : " + isMemberResult);
            
            GroupDTO dto = groupService.readGroup(groupNo);
            
            
            // 로그인된 사용자와 그룹장이 같으면 수정버튼 유무
            if (user.getUserNo() == dto.getUserNo()) {
                // 수정하기 버튼 활성화
                model.addAttribute("modifyButtonActive", true);
            } else {
                // 수정하기 버튼 비활성화
                model.addAttribute("modifyButtonActive", false);
            }
//          //로그인 유저가 가입한 그룹이라면 탈퇴하기 버튼 유무
//          List<GroupMemberDTO> groupMemberList = groupMemberService.getGroupMemberListByUser(user);
//      	
//          for (GroupMemberDTO groupMemberDTO : groupMemberList) {
//  			if(groupNo == groupMemberDTO.getGroupNo() && user!=null) {
//  				
//  				
//  			} else  {
//  				model.addAttribute("deleteJoin", false);		}
//  		}
            if(isMemberResult > 0) {
            	model.addAttribute("deleteJoin", true);
            } else {
            	model.addAttribute("deleteJoin", false);
            }
            
            model.addAttribute("group", dto);
            model.addAttribute("userNo", userNo);
            
            
			return  "/group/groupRead";
        }

	// 회원 그룹 가입하기
    @GetMapping("/join")
    public String joinGroup(@RequestParam("groupNo") Integer groupNo,
            @RequestParam("userNo") Integer userNo, Model model,
            HttpSession session) {
    	

		GroupMemberDTO groupMemberData = new GroupMemberDTO();
		groupMemberData.setGroupNo(groupNo);
		groupMemberData.setUserNo(userNo);
		groupMemberService.insertGroupMember(groupMemberData);
    	
		return "redirect:/group/groupIntroduce?groupNo=" + groupNo;
	}
    
    
  //가입한 그룹 탈퇴하기
    @GetMapping("/deleteJoin")
    public String deleteGroupMember(@RequestParam Integer groupNo, Model model,
            @ModelAttribute("groupDTO") GroupDTO groupDTO, 
            HttpSession session) {
    	
    	Integer userNo= 0;
    	Integer groupMemberNo = 0;
    	
        // 세션에서 사용자 정보 가져오기
        User user = (User) session.getAttribute("user");
        userNo= user.getUserNo();
        
        //user로 검색하는 groupMemberList 전체 가져오기
        List<GroupMemberDTO> getList = groupMemberService.getGroupMemberListByUser(user);
        for (GroupMemberDTO groupMemberDTO : getList) {
        	
        	// 로그인된 userNo + 해당 그룹의 groupNo가 리스트에 있다면
        	// groupMemberNo 저장하기
			if(groupMemberDTO.getUserNo() == userNo && groupMemberDTO.getGroupNo() == groupNo) {
				groupMemberNo = groupMemberDTO.getGroupMemberNo();
			}
		}
        
        groupMemberService.deleteGroupMember(groupMemberNo);
        
        return "redirect:/group/groupIntroduce?groupNo=" + groupNo;
    }
	
    /*
     *  그룹 수정 처리[값 검증]
     *   - @ModelAttribute @Valid GroupDTO dto : 화면의 입력데이터 저장(커맨드 객체)
     *    : 화면의 값을 커맨드 객체의 속성으로 바인딩
     *    : 입력값 문제 있을 때 다시 수정폼으로 이동해서 해당값을 그대로 세팅할 때 사용
     *   - @Valid : 커맨드 객체의 값이 설정한 조건에 맞는지 검증
     *   - BindingResult bindingResult : 검증한 결과 저장 객체
     *    : 안에는 GroupDTO 객체를 통해서 오류 값이 보관되어 있어서 다시 돌아간
     *      타임리프 페이지에서 오류 메시지 사용함.
     */

    /*
     *  그룹 수정 처리[값 검증]
     *   - @ModelAttribute @Valid GroupDTO dto : 화면의 입력데이터 저장(커맨드 객체)
     *    : 화면의 값을 커맨드 객체의 속성으로 바인딩
     *    : 입력값 문제 있을 때 다시 수정폼으로 이동해서 해당값을 그대로 세팅할 때 사용
     *   - @Valid : 커맨드 객체의 값이 설정한 조건에 맞는지 검증
     *   - BindingResult bindingResult : 검증한 결과 저장 객체
     *    : 안에는 GroupDTO 객체를 통해서 오류 값이 보관되어 있어서 다시 돌아간
     *      타임리프 페이지에서 오류 메시지 사용함.
     */
    @GetMapping("/groupModify")
    public void updateGroupForm(@RequestParam Integer groupNo,
    		@ModelAttribute("groupDTO") GroupDTO groupDTO,
    		BindingResult bindingResult,
    		Model model,
    		HttpSession session) {
    	log.info("groupModifyForm");
    	
    	User user = (User) session.getAttribute("user");
        Integer userNo = user.getUserNo();
        model.addAttribute("userNo", userNo);
        
    	GroupDTO dto = groupService.readGroup(groupNo);
    	
    	System.out.println("dto.toString : " + dto.toString());
    	model.addAttribute("group", dto);
    	
    	List<GroupCategoryDTO> groupCategoryList = groupCategoryService.getGroupCategoryList();
    	model.addAttribute("groupCategoryList", groupCategoryList);
    	List<RegionDTO> regionList = regionService.getRegionList();
    	model.addAttribute("regionList", regionList);
    	List<UserDTO> userList = userService.getUserList();
    	model.addAttribute("userList", userList);
    }
    
    @PostMapping("/groupModify")
	public String updateGroup(@ModelAttribute @Valid GroupDTO dto, 
								BindingResult bindingResult, 
								Model model) {

		log.info("updateGroup - post dto : " + dto.toString());

		// 검증시 오류 있으면
		if (bindingResult.hasErrors()) {
			// Log field errors
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			for (FieldError error : fieldErrors) {
				log.error(error.getField() + " " + error.getDefaultMessage());
			}
			GroupDTO groupDTO = groupService.readGroup(dto.getGroupNo());
			
			model.addAttribute("group", dto);
			
			List<GroupCategoryDTO> groupCategoryList = groupCategoryService.getGroupCategoryList();
	    	model.addAttribute("groupCategoryList", groupCategoryList);
	    	List<RegionDTO> regionList = regionService.getRegionList();
	    	model.addAttribute("regionList", regionList);
	    	List<UserDTO> userList = userService.getUserList();
	    	model.addAttribute("userList", userList);
			return "group/groupModify";
		}

		groupService.updateGroup(dto);

		return "redirect:/group/groupList";
	}
    
    
    @GetMapping("/groupDelete/{groupNo}")
    public String deleteGroup(@PathVariable("groupNo") Integer groupNo) {
        
    	boolean deleted = groupService.deleteGroup(groupNo);
    	
        return "redirect:/group/groupList";
    }
    
    /*
    @PostMapping("/search")
    public String searchGroup(GroupSearchDTO searchDTO, Model model) {
        // 그룹 검색 기능 구현
        // searchDTO를 사용하여 그룹을 검색하고 model에 결과를 담아서 리턴
        // 예시: List<GroupDTO> searchResult = groupService.searchGroup(searchDTO);
        // model.addAttribute("searchResult", searchResult);
        return "group/search";
    }
    */

    
    /*
     *	[ 채팅 관련 컨트롤러 맵핑 ]
     *	작성자 : 이민혁 
     */
    
    /*
     *	처음 채팅방에 입장 했을 때 보여지는 채팅 목록
     */
    @GetMapping("/chatList")
    public String chatList(Model model, @RequestParam("groupNo") Integer groupNo) {
    	List<ChatDTO> chatList = chatService.getChatListByGroupNo(groupNo);
    	chatList = chatList.stream().map(chat -> strReplace(chat)).collect(Collectors.toList());
    	
    	model.addAttribute("chatList", chatList);
    	model.addAttribute("groupNo", groupNo);
    	return "/groupMain/chatList";
    }
    
 
    
    /*
     *	채팅 입력 후 Ajax 요청으로 데이터를 저장하고 리스트를 모델에 저장하는 함수
     */
    @PostMapping("/chatList")
    @ResponseBody
    public ResponseEntity<List<ChatDTO>> chatList(Model model, @RequestBody FormData formData) {
    	// FormData 타입으로 채팅 입력과 관련된 데이터 전달 받음
    	Integer groupNo = formData.getGroupNo();
    	Integer userNo = formData.getUserNo();
    	String inputText = formData.getInputText();
    	
    	// 채팅 데이터 입력
    	ChatDTO chatDTO = ChatDTO.builder()
    			.chatContent(inputText)
    			.groupNo(groupNo)
    			.userNo(userNo)
    			.build();
    	chatService.insertChat(chatDTO);
    	
    	// 채팅 목록을 조회해서 MODEL 객체에 전달
    	List<ChatDTO> chatList = chatService.getChatListByGroupNo(groupNo);
    	// 문자열의 공백과 개행문자를 HTML 태그의 형태로 변환
    	chatList = chatList.stream().map(chat -> strReplace(chat)).collect(Collectors.toList());
    	model.addAttribute("chatList", chatList);
    	
    	return ResponseEntity.ok(chatList);
    }
    
    /*
     *  문자열의 공백과 개행문자를 HTML 태그의 형태로 변경하는 함수
     *  /chatList 맵핑으로 채팅 목록을 가져올 때 사용
     */
    private ChatDTO strReplace(ChatDTO chat) {
    	String replaceStr = chat.getChatContent().replace(" ", "&nbsp;");
    	replaceStr = replaceStr.replace("\n", "<br/>");
    	chat.setChatContent(replaceStr);
    	return chat;
    }
    /*
     *	모임 소개글로 이동하는 함수
     */
    @GetMapping("/groupIntroduce")
    public String groupIntroduce(Model model, @RequestParam("groupNo") Integer groupNo,
    							 HttpSession session) {
    	User user = (User)session.getAttribute("user");
    	boolean isUserBelongsToGroup = false;
    	if(user != null) {
    		Integer result = groupMemberService.isMemberOfGroup(user.getUserNo(), groupNo); // 회원이 그룹에 속해있는지 여부
    		if(result > 0) { isUserBelongsToGroup = true; } // 그룹에 속해 있음을 true로 변경
    	}
    	
    	model.addAttribute("groupNo", groupNo);
    	model.addAttribute("userExists", isUserBelongsToGroup);
    	return "/groupMain/groupIntroduce";
    }
    
}

/**
 * 
 * @author 505
 * 채팅 관련 폼 데이터를 받아줄 클래스 선언
 */
@Data
class FormData {
	
	private Integer groupNo;
	private Integer userNo;
	private String inputText;

}
