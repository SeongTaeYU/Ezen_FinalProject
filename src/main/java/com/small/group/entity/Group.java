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

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tbl_group")
public class Group extends BaseEntityWithTimeStamps {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long groupNo;
	
	@Column(nullable = false)
	private String groupName;
	
	@Column(nullable = false)
	private String groupDescription;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_category_no")
	private GroupCategory groupCategory;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_no")
	private Region region;
	
	@OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
	private List<GroupMember> groupMemberList;
	
	@OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
	private List<Board> boardList;
	
	@OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
	private List<Chat> chatList;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_no")
	private User user;
	
}
