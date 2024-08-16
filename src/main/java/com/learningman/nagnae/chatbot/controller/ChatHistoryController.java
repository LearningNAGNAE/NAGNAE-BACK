package com.learningman.nagnae.chatbot.controller;

import com.learningman.nagnae.domain.dto.ChatHistoryDto;
import com.learningman.nagnae.chatbot.service.ChatHistoryService;
import com.learningman.nagnae.authorization.util.JsonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/chat-history")
@RequiredArgsConstructor
public class ChatHistoryController {

    private final ChatHistoryService chatHistoryService;

    @GetMapping("/recent")
    public ResponseEntity<JsonResult> getRecentChats(@RequestParam("userNo") String StringuserNo) {
        log.info("chatbot.ChatHistoryController.getRecentChats() for userNo: {}", StringuserNo);
        int userNo = Integer.parseInt(StringuserNo);
        try {
            if (StringuserNo == null) {
                return ResponseEntity.badRequest().body(JsonResult.fail("User number is required"));
            }
            List<ChatHistoryDto> recentChats = chatHistoryService.getRecentChats(userNo);
            log.info("Returning {} recent chats for userNo: {}", recentChats.size(), userNo);
            return ResponseEntity.ok(JsonResult.success(recentChats));
        } catch (Exception e) {
            log.error("Error occurred while fetching recent chats for userNo: {}", userNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(JsonResult.fail("서버 오류가 발생했습니다: " + e.getMessage()));
        }
    }
    
    @GetMapping("/recent-detail")
    public ResponseEntity<JsonResult> getRecentChatDetail(@RequestParam("userNo") Integer userNo,
                                                          @RequestParam("chatHisNo") Integer chatHisNo) {
        log.info("chatbot.ChatHistoryController.getRecentChatDetail() for userNo: {} and chatHisNo: {}", userNo, chatHisNo);
        try {
            if (userNo == null) {
                return ResponseEntity.badRequest().body(JsonResult.fail("User number is required"));
            }
            List<ChatHistoryDto> recentChatDetails = chatHistoryService.getRecentChatDetails(userNo, chatHisNo);
            log.info("Returning {} recent chat details for userNo: {} and chatHisNo: {}", recentChatDetails.size(), userNo, chatHisNo);
            return ResponseEntity.ok(JsonResult.success(recentChatDetails));
        } catch (Exception e) {
            log.error("Error occurred while fetching recent chat details for userNo: {} and chatHisNo: {}", userNo, chatHisNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(JsonResult.fail("서버 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    @GetMapping("/{chatHisNo}")
    public ResponseEntity<JsonResult> getChatsByHistoryNo(@PathVariable Integer chatHisNo) {
        log.info("chatbot.ChatHistoryController.getChatsByHistoryNo() for chatHisNo: {}", chatHisNo);
        try {
            List<ChatHistoryDto> chatHistory = chatHistoryService.getChatsByHistoryNo(chatHisNo);
            log.info("Returning {} chat messages for chatHisNo: {}", chatHistory.size(), chatHisNo);
            return ResponseEntity.ok(JsonResult.success(chatHistory));
        } catch (Exception e) {
            log.error("Error occurred while fetching chat history for chatHisNo: {}", chatHisNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(JsonResult.fail("서버 오류가 발생했습니다: " + e.getMessage()));
        }
    }
}