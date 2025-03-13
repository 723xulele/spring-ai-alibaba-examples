package com.alibaba.cloud.ai.example.chat.openai.ordinaryway;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ollama/chat-client/ordinaryWay")
public class OllamaOrdinaryWayController {


    @Resource
    private SystemChatService chatService;



    /**
     * 普通方法实现调用预设好的问题
     */
    @GetMapping("/ask")
    public String ordinaryWay(@RequestParam String question) {
        return chatService.askSystemQuestion(question);
    }
}
