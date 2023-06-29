package com.small.group.repository;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.small.group.entity.GroupMember;
import com.small.group.entity.User;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

@SpringBootTest
@Transactional
@EnableJpaAuditing
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GroupMemberRepsitoryTest {

	@Autowired
	private GroupMemberRepository groupMemberRepository;
	
	@Test
	public void test() {
		User user = User.builder()
				.userNo(1)
				.build();
		
		List<GroupMember> list = groupMemberRepository.getGroupMemberByUser(user);
		assertNotNull(list);
		for(GroupMember m : list) {
			System.out.println("가입한 회원: " + m.getUser().getUserId() + ", 가입된 모임: " + m.getGroup().getGroupName());
		}
	}
}
