package com.small.group.service;

import java.util.List;

import com.small.group.dto.GroupMemberDTO;
import com.small.group.entity.GroupMember;


public interface GroupMemberService {
	
	GroupMember insertGroupMember(GroupMemberDTO groupMemberData);
	GroupMemberDTO readGroupMember(Integer groupMemberNo);
//	GroupMember updateGroupMember(GroupMemberDTO groupMemberData);
    Boolean deleteGroupMember(Integer groupMemberNo);
    List<GroupMemberDTO> getGroupMemberList();
    
    GroupMember dtoToEntity(GroupMemberDTO dto);
    GroupMemberDTO entityToDto(GroupMember entity);
    
}
