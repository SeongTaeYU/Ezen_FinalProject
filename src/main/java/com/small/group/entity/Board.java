package com.small.group.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@DynamicInsert
@Table(name = "tbl_board")
public class Board extends BaseEntityWithTimeStamps {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer boardNo;
	
	@Column(nullable = false)
	private String boardTitle;
	
	@Column(nullable = false)
	private String boardContent;
	
	@Column(nullable = false)
	@ColumnDefault("0")
	private Integer boardHit;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_category_no")
	private BoardCategory boardCategory;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_no")
	private Group group;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_no")
	private User user;
	
	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
	private List<Comment> commentList;
}
