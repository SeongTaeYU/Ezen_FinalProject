package com.small.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.small.group.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
