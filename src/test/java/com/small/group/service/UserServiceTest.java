package com.small.group.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Commit;

import com.small.group.dto.UserDTO;
import com.small.group.entity.User;

@SpringBootTest
@Transactional
@EnableJpaAuditing
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserServiceTest {

	@Autowired
	private UserService userService;
	

	
	@Test
//	@Commit
	public void test() {
		UserDTO user = UserDTO.builder()
				.userId("user01")
				.password("123456")
				.name("홍길동")
				.phone("010-1234-5678")
				.build();
		User result = userService.insertUser(user);
		assertNotNull(result);
	}
}
