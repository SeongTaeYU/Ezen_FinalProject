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
public class ChatDTO {

	private long chatNo;
	private String chatContent;
	
	private long groupNo;
	
	private long userNo;
	private String userName; // 회원 이름
	
	
	private LocalDateTime regDate;
	private LocalDateTime modDate;
}
