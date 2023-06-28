package com.small.group.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Entity
@Table(name = "tbl_group")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long groupNo;
	private String groupName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_no")
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
	
}
