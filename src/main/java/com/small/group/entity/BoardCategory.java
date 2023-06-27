package com.small.group.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Entity
@Table(name = "tbl_board_category")
public class BoardCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long boardCategoryNo;
	private String boardCategoryName;
}
