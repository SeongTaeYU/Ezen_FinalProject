package com.small.group.service;

import java.util.List;

import com.small.group.dto.ChatDTO;
import com.small.group.entity.Chat;


public interface ChatService {

	Chat insertChat(ChatDTO charData);
	ChatDTO readChat(Integer charNo);
	Chat updateChat(ChatDTO charData);
    Boolean deleteChat(Integer charNo);
    List<ChatDTO> getChatList();
}
