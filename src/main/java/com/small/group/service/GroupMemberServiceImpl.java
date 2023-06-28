package com.small.group.service;

import org.springframework.stereotype.Service;

import com.small.group.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupMemberServiceImpl implements GroupMemberService {

	private final GroupMemberRepository groupMemberRepository;
}
