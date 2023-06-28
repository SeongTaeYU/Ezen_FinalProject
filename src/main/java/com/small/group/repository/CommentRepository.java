package com.small.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.small.group.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
