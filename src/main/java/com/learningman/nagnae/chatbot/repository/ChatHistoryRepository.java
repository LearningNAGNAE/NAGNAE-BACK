package com.learningman.nagnae.chatbot.repository;

import com.learningman.nagnae.domain.dto.ChatHistoryDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ChatHistoryRepository {
    List<ChatHistoryDto> findRecentChats(Integer userNo);
    List<ChatHistoryDto> findRecentChatDetails(Integer userNo, Integer chatHisNo);
    List<ChatHistoryDto> findAllChatsByHistoryNo(Integer chatHisNo);
}