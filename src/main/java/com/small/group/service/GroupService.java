package com.small.group.service;

import java.util.List;

import com.small.group.dto.GroupDTO;
import com.small.group.entity.Group;


public interface GroupService {

	Group insertGroup(GroupDTO groupData);
	GroupDTO readGroup(Integer groupNo);
	Group updateGroup(GroupDTO groupData);
    Boolean deleteGroup(Integer groupNo);
    List<GroupDTO> getgroupList();
}
