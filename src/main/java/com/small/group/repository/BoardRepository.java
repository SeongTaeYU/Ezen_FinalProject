package com.small.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.small.group.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>{

}
