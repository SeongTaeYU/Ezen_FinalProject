package com.small.group.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.small.group.dto.UserDTO;
import com.small.group.entity.User;
import com.small.group.repository.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	
	//---------- 지우님 코드
	@PersistenceContext
	private EntityManager em;

	
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
	 *  ENTITY TO DTO
	 */
	private UserDTO entityToDto(User entity) {
		UserDTO dto = UserDTO.builder()
				.userNo(entity.getUserNo())
				.userId(entity.getUserId())
				.password(entity.getPassword())
				.name(entity.getName())
				.phone(entity.getPhone())
				.regDate(entity.getRegDate())
				.modDate(entity.getModDate())
				.build();
		return dto;
	}

	/**
	 * ----------------------------------
	 * 			C / R / U / D
	 * ----------------------------------
	 */
	
	/**
	 *	회원 저장하는 함수
	 */
	@Override
	public User insertUser(UserDTO userData) {
		User user = dtoToEntity(userData);
		return userRepository.save(user);
	}

	/**
	 *	회원 한 명 가져오는 함수
	 */
	@Override
	public UserDTO readUser(Integer userNo) {
		Optional<User> user = userRepository.findById(userNo);
		UserDTO userDTO = null;
		if(user.isPresent()) {
			userDTO = entityToDto(user.get());
		}
		return userDTO;
	}

	/**
	 *	회원 수정하는 함수
	 *	수정 가능한 속성(이름, 비밀번호, 휴대폰번호)
	 */
	@Override
	public User updateUser(UserDTO userData) {
		Optional<User> data = userRepository.findById(userData.getUserNo());
		if(data.isPresent()) {
			User targetEntity = data.get();
			targetEntity.setPassword(userData.getPassword());
			targetEntity.setName(userData.getName());
			targetEntity.setPhone(userData.getPhone());
			
			return userRepository.save(targetEntity);
		}
		return null;
	}

	/**
	 *	회원 삭제하는 함수
	 */
	@Override
	public Boolean deleteUser(Integer userNo) {
		Optional<User> data = userRepository.findById(userNo);
		if(data.isPresent()) {
			userRepository.delete(data.get());
			return true;
		}
		return false;
	}

	/**
	 *	회원 리스트를 가져오는 함수
	 */
	@Override
	public List<UserDTO> getUserList() {
		List<User> userList = userRepository.findAll();
		List<UserDTO> userDTOList = userList
				.stream().map(entity -> entityToDto(entity)).collect(Collectors.toList());
		return userDTOList;
	}
	
	// ----------------------지우님 코드-------------
	@Override
    public boolean loginCheck(UserDTO userDTO) {
        Long count = 0L;
        count = em.createQuery("select count(*) from tbl_user u where u.userId = :userId and u.password = :password", Long.class)
                .setParameter("userId", userDTO.getUserId())
                .setParameter("password", userDTO.getPassword())
                .getSingleResult();

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

	
	
	
	
}
