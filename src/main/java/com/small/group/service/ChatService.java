package com.small.group.service;

import java.util.List;

import com.small.group.dto.ChatDTO;
import com.small.group.entity.Chat;


public interface ChatService {

	Chat insertChat(ChatDTO chatData);
	ChatDTO readChat(Integer chatNo);
	Chat updateChat(ChatDTO chatData);
    Boolean deleteChat(Integer chatNo);
    List<ChatDTO> getChatList();
}
