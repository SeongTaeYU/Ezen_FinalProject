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
@Table(name = "tbl_region")
public class Region {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long regionNo;
	
	@Column(nullable = false)
	private String regionName;
	
	@OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
	private List<Group> groupList;
}
