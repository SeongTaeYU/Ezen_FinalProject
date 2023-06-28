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
public class UserDTO {

	private long userNo;
	private String userId;
	private String password;
	private String name;
	private String phone;
	
	private LocalDateTime regDate;
	private LocalDateTime modDate;
}
