package com.small.group.service;

import java.util.List;

import com.small.group.dto.BoardCategoryDTO;
import com.small.group.entity.BoardCategory;

public interface BoardCategoryService {
	
	BoardCategory insertBoardCategory(BoardCategoryDTO boardCategoryData);
	BoardCategoryDTO readBoardCategory(Integer boardCategoryNo);
	BoardCategory updateBoardCategory(BoardCategoryDTO boardCategoryData);
    Boolean deleteBoardCategory(Integer boardCategoryNo);
    List<BoardCategoryDTO> getBoardCategoryList();
    
    BoardCategory dtoToEntity(BoardCategoryDTO dto);
    BoardCategoryDTO entityToDto(BoardCategory entity);
}
