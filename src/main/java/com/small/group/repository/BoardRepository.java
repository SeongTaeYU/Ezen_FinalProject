package com.small.group.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.small.group.entity.Board;
import com.small.group.entity.Group;

public interface BoardRepository extends JpaRepository<Board, Integer>{

	List<Board> findByGroup(Group group);
}
