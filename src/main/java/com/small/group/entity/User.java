package com.small.group.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Entity
@Table(name = "tbl_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userNo;
	private String userId;
	private String password;
	private String name;
	private String nickname;
	private Date birth;
	private String region;
	private String email;
	private String phone;
	private String gender;
	
	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	private Group group;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private GroupMember groupMember;
	
	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	private Board board;
}
