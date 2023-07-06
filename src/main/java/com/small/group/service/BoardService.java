package com.small.group.service;

import java.util.List;

import com.small.group.dto.BoardDTO;
import com.small.group.dto.PageRequestDTO;
import com.small.group.dto.PageResultDTO;
import com.small.group.entity.Board;


public interface BoardService {

	Board insertBoard(BoardDTO boardData);
	BoardDTO readBoard(Integer boardNo);
	Board updateBoard(BoardDTO boardData);
    Boolean deleteBoard(Integer boardNo);
    List<BoardDTO> getBoardList();
    List<BoardDTO> getBoardListByGroupNo(Integer groupNo);
    
    Board dtoToEntity(BoardDTO dto);
    BoardDTO entityToDto(Board entity);
    
    // 게시판 페이지
    public PageResultDTO<BoardDTO, Board> getBoardList(PageRequestDTO requestDTO);
    void updateBoardHit(Integer boardNo);
}
