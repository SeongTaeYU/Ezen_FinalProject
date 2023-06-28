package com.small.group.entity;

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
@Table(name = "tbl_group_member")
public class GroupMember extends BaseEntityWithTimeStamps {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long groupMemberNo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_no")
	private Group group;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_no")
	private User user;

}
