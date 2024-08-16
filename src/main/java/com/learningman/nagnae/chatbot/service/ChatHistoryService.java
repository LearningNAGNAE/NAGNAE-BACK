package com.learningman.nagnae.chatbot.service;

import com.learningman.nagnae.domain.dto.ChatHistoryDto;
import com.learningman.nagnae.chatbot.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatHistoryService {
    private final ChatHistoryRepository chatHistoryRepository;
    
    public List<ChatHistoryDto> getRecentChats(Integer userNo) {
        return chatHistoryRepository.findRecentChats(userNo);
    }
    
    public List<ChatHistoryDto> getRecentChatDetails(Integer userNo, Integer chatHisNo) {
        return chatHistoryRepository.findRecentChatDetails(userNo, chatHisNo);
    }
    
    public List<ChatHistoryDto> getChatsByHistoryNo(Integer chatHisNo) {
        return chatHistoryRepository.findAllChatsByHistoryNo(chatHisNo);
    }
}