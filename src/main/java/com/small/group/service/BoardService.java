package com.small.group.service;

import java.util.List;

import com.small.group.dto.BoardDTO;
import com.small.group.entity.Board;


public interface BoardService {

	Board insertBoard(BoardDTO boardData);
	BoardDTO readBoard(Integer boardNo);
	Board updateBoard(BoardDTO boardData);
    Boolean deleteBoard(Integer boardNo);
    List<BoardDTO> getBoardCategoryList();
}
