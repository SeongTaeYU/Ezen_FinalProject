package com.small.group.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.small.group.entity.GroupMember;
import com.small.group.entity.User;

import org.springframework.data.repository.query.Param;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer> {

	@Query("select m from GroupMember m where m.user = :user")
	public List<GroupMember> getGroupMemberByUser(@Param("user") User user);
	
	@Query("select count(*) from GroupMember where user_no = :userNo and group_no = :groupNo")
	public Integer isMemberOfGroup(@Param("userNo") Integer userNo, @Param("groupNo") Integer groupNo);
}
