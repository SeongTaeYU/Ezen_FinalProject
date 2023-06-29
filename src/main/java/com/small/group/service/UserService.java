package com.small.group.service;

import com.small.group.dto.UserDTO;
import com.small.group.entity.User;

public interface UserService {

	User insertUser(UserDTO userData);
}
