package com.small.group.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Commit;

import com.small.group.entity.Board;
import com.small.group.entity.BoardCategory;

@SpringBootTest
@EnableJpaAuditing
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BoardRepositoryTest {
	
	@Autowired
	private BoardCategoryRepository boardCategoryRepository;
	
	@Test
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

}
