package com.small.group.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.small.group.dto.PageRequestDTO;
import com.small.group.dto.PageResultDTO;
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
	public User dtoToEntity(UserDTO dto) {
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
	public UserDTO entityToDto(User entity) {
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
	public boolean deleteUser(Integer userNo) {
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
	public PageResultDTO<UserDTO, User> getList(PageRequestDTO requestDTO){
		Pageable pageable = requestDTO.getPageable(Sort.by("userNo").descending());
		Page<User> result = userRepository.findAll(pageable);
		Function<User, UserDTO> fn = user -> entityToDto(user);
		return new PageResultDTO<>(result, fn);
	}
	/*
	 *	이 부분 @Query 형식으로 가져오기. 
	 *	페이징이 필요한 부분 구분하기.
	 */
	
	
	
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
	
	@Override
	public boolean idExist(String userId) {
		
		log.info("서비스에서 아이디 중복체크 시작");
		System.out.println("userId : " + userId);
		Long count = 0L;
		System.out.println("count 수량1111 @@@@@ " + count);
	    count = em.createQuery("SELECT COUNT(*) FROM tbl_user u WHERE u.userId = :userId", Long.class)
	            .setParameter("userId", userId)
	            .getSingleResult();
	    
	  System.out.println(" count 수량 @@@@@ " + count);
	    if (count > 0) {
	    	return true;
		}else {
			return false;
		}
    
	}

	@Override
	public User register(UserDTO userDTO) {
		User entity = dtoToEntity(userDTO);
		return userRepository.save(entity);
	}
	
	@Override
	public User login(UserDTO userDTO) {
		User user = dtoToEntity(userDTO);
		User result = userRepository.findByIdPwd(user.getUserId(), user.getPassword());
		return result;
	}
	
	@Override
	public List<UserDTO> getUserByNo(Integer userNo) {
		List<User> userByNo = userRepository.getUserByNo(userNo);
		List<UserDTO> userDTOList = userByNo
				.stream().map(entity -> entityToDto(entity)).collect(Collectors.toList());
		return userDTOList;
	}
	
	
	
}
