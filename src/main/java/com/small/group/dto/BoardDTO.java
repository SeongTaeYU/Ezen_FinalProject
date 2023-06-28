package com.small.group.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
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
	
	@NotBlank(message = "제목을 입력하세요.")
	@Size(min = 1, max = 50, message = "제목은 1 ~ 50자 이내로 작성하세요.")
	private String boardTitle;
	
	@NotBlank(message = "내용을 입력하세요.")
	@Size(min = 1, max = 500, message = "내용은 1 ~ 500자 이내로 작성하세요.")
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