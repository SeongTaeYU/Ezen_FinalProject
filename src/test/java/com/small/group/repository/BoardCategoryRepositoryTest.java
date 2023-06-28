package com.small.group.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Commit;

import com.small.group.entity.Board;
import com.small.group.entity.BoardCategory;

import groovy.transform.AutoExternalize;

@SpringBootTest
@EnableJpaAuditing
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BoardCategoryRepositoryTest {
	
	@Autowired
	private BoardCategoryRepository boardCategoryRepository;
	
	@Autowired
	private BoardRepository boardRepository;
	
//	@Test
	@Commit
	public void test() {
		String boardCategoryList[] = {"공지사항", "가입인사", "자유 글", "정모후기", "관심사 공유"};
		for(String categoryName : boardCategoryList) {
			BoardCategory boardCategory = BoardCategory.builder()
					.boardCategoryName(categoryName)
					.build();
			boardCategoryRepository.save(boardCategory);
		}
	}
	
	@Test
	public void test2() {
		BoardCategory category = boardCategoryRepository.findById(3).get();
		assertNotNull(category);
//		IntStream.rangeClosed(1, 100).forEach(i -> {
//			
//		});
	}
	

}
