package com.small.group.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

	private long boardNo;
	
	@Size(max = 20)
	private String boardTitle;
	
	@Size(max = 500)
	private String boardContent;
	
	private long boardHit;
	
	private long categoryNo;
	private String categoryName; // 카테고리 이름
	
	private long groupNo;
	
	private long userNo;
	private String userName; // 유저 이름
	
	private LocalDateTime regDate;
	private LocalDateTime modDate;
}
