package com.small.group.service;

import java.util.List;

import com.small.group.dto.GroupMemberDTO;
import com.small.group.entity.GroupMember;
import com.small.group.entity.User;


public interface GroupMemberService {
	
	GroupMember insertGroupMember(GroupMemberDTO groupMemberData);
	GroupMemberDTO readGroupMember(Integer groupMemberNo);
//	GroupMember updateGroupMember(GroupMemberDTO groupMemberData);
    Boolean deleteGroupMember(Integer groupMemberNo);
    List<GroupMemberDTO> getGroupMemberList();
    List<GroupMemberDTO> getGroupMemberListByUser(User user);
    Integer isMemberOfGroup(Integer userNo, Integer groupNo);
    
    GroupMember dtoToEntity(GroupMemberDTO dto);
    GroupMemberDTO entityToDto(GroupMember entity);
    
}
