package com.small.group.service;

import org.springframework.stereotype.Service;

import com.small.group.dto.UserDTO;
import com.small.group.entity.User;
import com.small.group.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;

	
	/**
	 * DTO TO ENTITY 
	 */
	private User dtoToEntity(UserDTO dto) {
		User entity = User.builder()
				.userId(dto.getUserId())
				.password(dto.getPassword())
				.name(dto.getName())
				.phone(dto.getPhone())
				.build();
		return entity;
	}

	
	/**
	 * 회원 정보를 데이터베이스에 저장하는 함수
	 */
	@Override
	public User insertUser(UserDTO userData) {
		User user = dtoToEntity(userData);
		return userRepository.save(user);
	} 
	
	
}
