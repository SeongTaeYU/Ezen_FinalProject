package com.small.group.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<GroupMember> groupMember;
	
}
