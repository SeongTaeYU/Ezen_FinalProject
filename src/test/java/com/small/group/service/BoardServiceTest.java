package com.small.group.service;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.small.group.entity.BoardCategory;

@SpringBootTest
@Transactional
@EnableJpaAuditing
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BoardServiceTest {

	@Autowired
	private BoardCategoryService boardCategoryService;
	
	@Autowired
	private BoardService boardService;
	
	@Test
	public void test() {
		
		BoardCategory boardCategory = boardCategoryService.getBoardCategoryByNo(3);
		
		
	}
}
