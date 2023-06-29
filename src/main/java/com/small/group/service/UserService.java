package com.small.group.service;

import java.util.List;

import com.small.group.dto.UserDTO;
import com.small.group.entity.User;

public interface UserService {

	User insertUser(UserDTO userData);
	UserDTO readUser(Integer userNo);
	User updateUser(UserDTO userData);
    Boolean deleteUser(Integer userNo);
    List<UserDTO> getUserList();
    
    // ------- 지우님 코드
	boolean loginCheck(UserDTO userDTO);
}
