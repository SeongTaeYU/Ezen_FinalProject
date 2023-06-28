package com.small.group.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

	private long commentNo;
	private String commentContent;
	
	private long boardNo;
	
	private long userNo;
	private String userName; // 회원 이름
	
	private LocalDateTime regDate;
	private LocalDateTime modDate;
}
