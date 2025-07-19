package indi.mofan.controller;


import indi.mofan.service.ChatPersistenceAssistant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author mofan
 * @date 2025/7/19 15:09
 */
@RestController
public class ChatPersistenceController {
    @Resource
    private ChatPersistenceAssistant chatPersistenceAssistant;

    @GetMapping("/chat-persistence/redis")
    public String test() {
        chatPersistenceAssistant.chat(1L, "你好，我的名字是 redis");
        chatPersistenceAssistant.chat(2L, "你好，我的名字是 nacos");

        String chat1 = chatPersistenceAssistant.chat(1L, "我的名字是什么？");
        System.out.println(chat1);

        String chat2 = chatPersistenceAssistant.chat(2L, "我的名字是什么？");
        System.out.println(chat2);

        return "success: " + LocalDateTime.now();
    }
}
