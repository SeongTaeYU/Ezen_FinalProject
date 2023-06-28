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
public class CommentDTO {

	private long commentNo;
	
	@NotBlank(message = "내용을 입력하세요.")
	@Size(min = 1, max = 100, message = "댓글은 1 ~ 100자 이내로 작성하세요.")
	private String commentContent;
	
	private long boardNo;
	
	private long userNo;
	private String userName; // 회원 이름
	
	private LocalDateTime regDate;
	private LocalDateTime modDate;
}
