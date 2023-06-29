package com.small.group.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.small.group.dto.ChatDTO;
import com.small.group.entity.Chat;
import com.small.group.entity.Group;
import com.small.group.entity.User;
import com.small.group.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

	private final ChatRepository chatRepository;
	private final GroupRepository groupRepository;
	private final UserRepository userRepository;
	
	/**
	 *  DTO TO ENTITY
	 */
	private Chat dtoToEntity(ChatDTO dto) {
		Optional<Group> optGroup = groupRepository.findById(dto.getGroupNo());
		Group group = (optGroup.isPresent()) ? optGroup.get() : null;
		
		Optional<User> optUser = userRepository.findById(dto.getUserNo());
		User user = (optUser.isPresent()) ? optUser.get() : null;
		
		
		Chat entity = Chat.builder()
				.chatContent(dto.getChatContent())
				.group(group)
				.user(user)
				.build();
		return entity;
	}
	
	/**
	 *  ENTITY TO DTO
	 */
	private ChatDTO entityToDto(Chat entity) {
		Integer groupNo = entity.getGroup().getGroupNo();
		Integer userNo = entity.getUser().getUserNo();
		String userName = entity.getUser().getName();
		
		ChatDTO dto = ChatDTO.builder()
				.chatNo(entity.getChatNo())
				.chatContent(entity.getChatContent())
				.groupNo(groupNo)
				.userNo(userNo)
				.userName(userName)
				.regDate(entity.getRegDate())
				.modDate(entity.getModDate())
				.build();
		return dto;
	}
	
	
	/**
	 * ----------------------------------
	 * 			C / R / U / D
	 * ----------------------------------
	 */
	
	/**
	 *	채팅 저장하는 함수
	 */
	@Override
	public Chat insertChat(ChatDTO charData) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *	채팅 한 개 가져오는 함수
	 */
	@Override
	public ChatDTO readChat(Integer charNo) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *	채팅 수정하는 함수
	 */
	@Override
	public Chat updateChat(ChatDTO charData) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *	채팅 삭제하는 함수
	 */
	@Override
	public Boolean deleteChat(Integer charNo) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *	채팅 리스트를 가져오는 함수
	 */
	@Override
	public List<ChatDTO> getChatList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
