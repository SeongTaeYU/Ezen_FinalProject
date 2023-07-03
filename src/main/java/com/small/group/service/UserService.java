package com.small.group.service;

import java.util.List;

import com.small.group.dto.PageRequestDTO;
import com.small.group.dto.PageResultDTO;
import com.small.group.dto.UserDTO;
import com.small.group.entity.User;

public interface UserService {

	PageResultDTO<UserDTO, User> getList(PageRequestDTO requestDTO);
	List<UserDTO> getList();
	User insertUser(UserDTO userData);
	UserDTO readUser(Integer userNo);
	User updateUser(UserDTO userData);
    boolean deleteUser(Integer userNo);
    
    // ------- 지우님 코드
    User register(UserDTO userDTO);
	boolean idExist(String userId);
	boolean loginCheck(UserDTO userDTO);
}
