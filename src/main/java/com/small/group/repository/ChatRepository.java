package com.small.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.small.group.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Integer>{

}
