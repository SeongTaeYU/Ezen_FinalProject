package com.small.group.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tbl_user")
public class User extends BaseEntityWithTimeStamps {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userNo;
	
	@Column(nullable = false)
	private String userId;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String phone;
	
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Group> groupList;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<GroupMember> groupMemberList;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Comment> commentList;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Chat> chatList;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Board> boardList;
	
}
