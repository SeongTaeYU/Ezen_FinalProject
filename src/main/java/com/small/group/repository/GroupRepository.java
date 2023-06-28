package com.small.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.small.group.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {

}
