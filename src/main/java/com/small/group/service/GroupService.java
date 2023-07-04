package com.small.group.service;

import java.util.List;

import com.small.group.dto.GroupDTO;
import com.small.group.dto.PageRequestDTO;
import com.small.group.dto.PageResultDTO;
import com.small.group.entity.Group;
import com.small.group.entity.GroupCategory;
import com.small.group.entity.Region;
import com.small.group.entity.User;


public interface GroupService {

	Group insertGroup(GroupDTO groupData);
	GroupDTO readGroup(Integer groupNo);
	Group updateGroup(GroupDTO groupData);
    Boolean deleteGroup(Integer groupNo);
    List<GroupDTO> getGroupList();
    
    Group dtoToEntity(GroupDTO dto);
    GroupDTO entityToDto(Group entity);
    
 	PageResultDTO<GroupDTO, Group> getGroupList(PageRequestDTO requestDTO); // 그룹(모임) 페이지
 	List<GroupDTO> getGroupList(String keyword); // 키워드로 그룹 검색
 	
 	
 	
 	
    
}
