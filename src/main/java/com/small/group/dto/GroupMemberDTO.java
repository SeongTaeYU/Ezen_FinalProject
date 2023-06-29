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
public class GroupMemberDTO {

	private Integer groupMemberNo;
	private Integer groupNo;
	private Integer userNo;
	
	private LocalDateTime regDate;
	private LocalDateTime modDate;
}
