package com.small.group.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.small.group.dto.GroupMemberDTO;
import com.small.group.entity.Group;
import com.small.group.entity.GroupMember;
import com.small.group.entity.User;
import com.small.group.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupMemberServiceImpl implements GroupMemberService {

	private final GroupMemberRepository groupMemberRepository;
	private final GroupRepository groupRepository;
	private final UserRepository userRepository;

	
	/**
	 *  DTO TO ENTITY
	 */
	private GroupMember dtoToEntity(GroupMemberDTO dto) {
		Optional<Group> optGroup = groupRepository.findById(dto.getGroupNo());
		Group group = (optGroup.isPresent()) ? optGroup.get() : null;
		
		Optional<User> optUser = userRepository.findById(dto.getUserNo());
		User user = (optUser.isPresent()) ? optUser.get() : null;
				
		GroupMember entity = GroupMember.builder()
				.group(group)
				.user(user)
				.build();
		return entity;
	}
	
	/**
	 *  ENTITY TO DTO
	 */
	private GroupMemberDTO entityToDto(GroupMember entity) {
		Integer groupNo = entity.getGroup().getGroupNo();
		Integer userNo = entity.getUser().getUserNo();
		
		GroupMemberDTO dto = GroupMemberDTO.builder()
				.groupMemberNo(entity.getGroupMemberNo())
				.groupNo(groupNo)
				.userNo(userNo)
				.build();
		return dto;
	}
	
	/**
	 * ----------------------------------
	 * 			C / R / U / D
	 * ----------------------------------
	 */
	
	/**
	 *	모임 멤버 저장하는 함수
	 */
	@Override
	public GroupMember insertGroupMember(GroupMemberDTO groupMemberData) {
		GroupMember groupMember = dtoToEntity(groupMemberData);
		return groupMemberRepository.save(groupMember);
	}

	/**
	 *	모임 멤버 한 개 가져오는 함수
	 */
	@Override
	public GroupMemberDTO readGroupMember(Integer groupMemberNo) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *	모임 멤버 수정하는 함수
	 */
	@Override
	public GroupMember updateGroupMember(GroupMemberDTO groupMemberData) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *	모임 멤버 삭제하는 함수
	 */
	@Override
	public Boolean deleteGroupMember(Integer groupMemberNo) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *	모임 멤버 리스트를 가져오는 함수
	 */
	@Override
	public List<GroupMemberDTO> getGroupMemberList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
