package com.small.group.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Entity
@Table(name = "tbl_board")
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long boardNo;
	private String boardTitle;
	private String boardContent;
	private long boardHit;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "boardCategoryNo")
	private BoardCategory boardCategory;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "groupNo")
	private Group group;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userNo")
	private User user;
}
