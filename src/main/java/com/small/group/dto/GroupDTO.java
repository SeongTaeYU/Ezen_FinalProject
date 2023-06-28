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
public class GroupDTO {

	private long groupNo;
	private String groupName;
	
	private long groupCategoryNo;
	private String groupCategoryName; // 모임 카테고리 이름
	
	private long regionNo;
	private String regionName; // 지역 이름
	
	private long userNo;
	private long readerUserName; // 모임장 이름
	
	private LocalDateTime regDate;
	private LocalDateTime modDate;
}
