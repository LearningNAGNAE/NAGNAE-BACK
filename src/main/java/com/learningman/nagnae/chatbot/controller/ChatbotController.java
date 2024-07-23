package com.learningman.nagnae.chatbot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatbotController {

    @GetMapping("/")
    public String main() {
        return "ok";
    }

}
