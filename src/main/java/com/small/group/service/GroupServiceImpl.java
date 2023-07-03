package com.small.group.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.small.group.dto.GroupDTO;
import com.small.group.dto.PageRequestDTO;
import com.small.group.dto.PageResultDTO;
import com.small.group.entity.Group;
import com.small.group.entity.GroupCategory;
import com.small.group.entity.Region;
import com.small.group.entity.User;
import com.small.group.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

	private final GroupRepository groupRepository;
	private final GroupCategoryRepository groupCategoryRepository;
	private final RegionRepository regionRepository;
	private final UserRepository userRepository;
	
	/**
	 *  DTO TO ENTITY
	 */
	private Group dtoToEntity(GroupDTO dto) {
		Optional<GroupCategory> optGroupCategory = groupCategoryRepository.findById(dto.getGroupCategoryNo());
		Optional<Region> optRegion = regionRepository.findById(dto.getRegionNo());
		Optional<User> optUser = userRepository.findById(dto.getUserNo());
		
		GroupCategory groupCategory = (optGroupCategory.isPresent()) ? optGroupCategory.get() : null;
		Region region = (optRegion.isPresent()) ? optRegion.get() : null;
		User user = (optUser.isPresent()) ? optUser.get() : null;
		
		Group entity = Group.builder()
				.groupName(dto.getGroupName())
				.groupDescription(dto.getGroupDescription())
				.groupCategory(groupCategory)
				.region(region)
				.user(user)
				.build();
		
		return entity;
	}
	/**
	 *  ENTITY TO DTO
	 */
	private GroupDTO entityToDto(Group entity) {
		Integer groupCategoryNo = entity.getGroupCategory().getGroupCategoryNo();
		String groupCategoryName = entity.getGroupCategory().getGroupCategoryName();
		Integer regionNo = entity.getRegion().getRegionNo();
		String regionName = entity.getRegion().getRegionName();
		Integer userNo = entity.getUser().getUserNo();
		String readerUserName = entity.getUser().getName();
		
		GroupDTO dto = GroupDTO.builder()
				.groupNo(entity.getGroupNo())
				.groupName(entity.getGroupName())
				.groupDescription(entity.getGroupDescription())
				.groupCategoryNo(groupCategoryNo)
				.groupCategoryName(groupCategoryName)
				.regionNo(regionNo)
				.regionName(regionName)
				.userNo(userNo)
				.readerUserName(readerUserName)
				.regDate(entity.getRegDate())
				.modDate(entity.getModDate())
				.build();
		
		return dto;
	}
	
	/**
	 * ----------------------------------
	 * 			C / R / U / D
	 * ----------------------------------
	 */
	
	/**
	 *	모임 저장하는 함수
	 */
	@Override
	public Group insertGroup(GroupDTO groupData) {
		Group group = dtoToEntity(groupData);
		return groupRepository.save(group);
	}

	/**
	 *	모임 한 개 가져오는 함수
	 */
	@Override
	public GroupDTO readGroup(Integer groupNo) {
		Optional<Group> group = groupRepository.findById(groupNo);
		GroupDTO groupDTO = null;
		if(group.isPresent()) {
			groupDTO = entityToDto(group.get());
		}
		return groupDTO;
	}

	/**
	 *	모임 수정하는 함수
	 */
	@Override
	public Group updateGroup(GroupDTO groupData) {
		Optional<Group> data = groupRepository.findById(groupData.getGroupNo());
		if(data.isPresent()) {
			Group targetEntity = data.get();
			targetEntity.setGroupName(null);
			targetEntity.setGroupDescription(null);
			
			return groupRepository.save(targetEntity);
		}
		return null;
	}

	/**
	 *	모임 삭제하는 함수
	 */
	@Override
	public Boolean deleteGroup(Integer groupNo) {
		Optional<Group> data = groupRepository.findById(groupNo);
		if(data.isPresent()) {
			groupRepository.delete(data.get());
			return true;
		}
		return false;
	}

	/**
	 *	모임 리스트를 가져오는 함수
	 */
	@Override
	public List<GroupDTO> getGroupList() {
		List<Group> groupList = groupRepository.findAll();
		List<GroupDTO> groupDTOList = groupList
				.stream().map(entity -> entityToDto(entity)).collect(Collectors.toList());
		return groupDTOList;
	}
	
	/**
	 *	그룹(모임) 목록 조회[페이징]
	 */
    @Override
    public PageResultDTO<GroupDTO, Group> getGroupList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("groupNo").descending());
        Page<Group> result = groupRepository.findAll(pageable);
        Function<Group, GroupDTO> fn = (entity -> entityToDto(entity)); // java.util.Function
        return new PageResultDTO<>(result, fn );
    }
	
}
