package com.small.group.repository;

import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Commit;

import com.small.group.entity.BoardCategory;
import com.small.group.entity.GroupCategory;
import com.small.group.entity.Region;
import com.small.group.entity.User;
import com.small.group.repository.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DummyDataFiles {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RegionRepository regionRepository;
	
	@Autowired
	private BoardCategoryRepository boardCategoryRepository;
	
	@Autowired
	private GroupCategoryRepository groupCategoryRepository;
	
	
	/**
	 *	회원 데이터 입력
	 */
//	@Test
//	@Commit
	public void userDataInsert() {
		IntStream.rangeClosed(15, 20).forEach(i -> {
			User user = User.builder()
					.name("홍길동" + i)
					.userId("user0" + i)
					.password("123456")
					.phone("010-1111-2222")
					.build();
			userRepository.save(user);
		});
	}
	
	
	/**
	 *	지역 데이터 입력
	 */
//	@Test
//	@Commit
	public void regionDataInsert() {
		String regionList[] = {"수원시", "성남시","고양시","용인시","부천시","안산시","안양시","남양주시","화성시","평택시","의정부시","시흥시","파주시","김포시","광명시","군포시","이천시","양주시",
							   "오산시","구리시","안성시","포천시","의왕시","여주시","동두천시","과천시","광주시","여주시","파주시"};
		for(String regionStr : regionList) {
			Region region = Region.builder()
					.regionName(regionStr)
					.build();
			regionRepository.save(region);
		}
	}
	
	/**
	 *	게시글 카테고리 데이터 입력
	 */
//	@Test
//	@Commit
	public void boardCategoryDataInsert() {
		String boardCategoryList[] = {"공지사항", "가입인사", "자유 글", "정모후기", "관심사 공유"};
		for(String categoryName : boardCategoryList) {
			BoardCategory boardCategory = BoardCategory.builder()
					.boardCategoryName(categoryName)
					.build();
			boardCategoryRepository.save(boardCategory);
		}
	}
	
	/**
	 *	모임 카테고리 데이터 입력
	 */
//	@Test
//	@Commit
	public void groupCategoryDataInsert() {
		String groupCategoryList[] = {"문화/예술", "운동", "맛집", "여행", "자기계발", "친목", "소개팅", "자유주제"};
		for(String categoryName : groupCategoryList) {
			GroupCategory groupCategory = GroupCategory.builder()
					.groupCategoryName(categoryName)
					.build();
			
			groupCategoryRepository.save(groupCategory);
		}
		
	}
	

	
}
