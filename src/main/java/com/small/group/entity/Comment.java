package com.small.group.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tbl_comment")
public class Comment extends BaseEntityWithTimeStamps {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long commentNo;
	
	@Column(nullable = false)
	private String commentContent;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_no")
	private Board board;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_no")
	private User user;
	
}
