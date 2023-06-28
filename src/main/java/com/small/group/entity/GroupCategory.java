package com.small.group.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
@Entity
@Table(name = "tbl_group_category")
public class GroupCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long groupCategoryNo;
	private String groupCategoryName;
	
	@OneToMany(mappedBy = "groupCategory", fetch = FetchType.LAZY)
	private List<Group> groupList;
}
