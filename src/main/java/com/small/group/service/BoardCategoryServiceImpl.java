package com.small.group.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.small.group.entity.BoardCategory;
import com.small.group.repository.BoardCategoryRepository;
import com.small.group.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardCategoryServiceImpl implements BoardCategoryService {

	private final BoardCategoryRepository boardCategoryRepository;

	// 게시글 카테고리 한 개 가져오는 함수
	@Override
	public BoardCategory getBoardCategoryByNo(Integer categoryNo) {
		Optional<BoardCategory> data = boardCategoryRepository.findById(categoryNo);
		BoardCategory category = null;
		if(data != null) {
			category = data.get();
		}
		
		return category;
	}
	
	
}
