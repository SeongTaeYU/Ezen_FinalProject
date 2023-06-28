package com.small.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.small.group.entity.GroupMember;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer> {

}
