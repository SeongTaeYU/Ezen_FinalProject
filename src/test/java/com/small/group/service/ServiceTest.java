package com.small.group.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Commit;

import com.small.group.dto.ChatDTO;
import com.small.group.dto.GroupDTO;
import com.small.group.entity.Board;
import com.small.group.entity.BoardCategory;
import com.small.group.entity.Group;
import com.small.group.entity.User;
import com.small.group.repository.BoardCategoryRepository;
import com.small.group.repository.BoardRepository;
import com.small.group.repository.GroupRepository;
import com.small.group.repository.UserRepository;


@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ServiceTest {

	@Autowired
	private GroupService groupService;
	
	@Test
	public void test() {
		String keyword = "오리";
		List<GroupDTO> groupList = groupService.getGroupList(keyword);
		
		for(GroupDTO dto : groupList) {
			System.out.println("Group Name: " + dto.getGroupCategoryName());
			System.out.println("Group Description: " + dto.getGroupDescription());
		}
	}
}
