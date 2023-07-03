package com.small.group.repository;

import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Commit;

import com.small.group.entity.User;
import com.small.group.repository.*;

@SpringBootTest
@Transactional
@EnableJpaAuditing
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DummyDataFiles {

	@Autowired
	private UserRepository userRepository;
	
	
	/**
	 *	회원 데이터 입력
	 */
	@Test
	@Commit
	public void userDataInsert() {
		IntStream.rangeClosed(1, 9).forEach(i -> {
			User user = User.builder()
					.name("홍길동" + i)
					.userId("user0" + i)
					.password("123456")
					.phone("010-1111-2222")
					.build();
			userRepository.save(user);
		});
	}
	
}
