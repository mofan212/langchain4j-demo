package indi.mofan.controller;


import indi.mofan.service.ChatAssistant;
import indi.mofan.service.ChatMemoryAssistant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author mofan
 * @date 2025/7/15 20:37
 */
@RestController
public class ChatMemoryController {

    @Resource(name = "chat")
    private ChatAssistant chatAssistant;

    @Resource(name = "chatMessageWindowChatMemory")
    private ChatMemoryAssistant chatMessageWindowChatMemory;

    @Resource(name = "chatTokenWindowChatMemory")
    private ChatMemoryAssistant chatTokenWindowChatMemory;

    @GetMapping("/chat-memory/chat")
    public String chat() {
        String ans01 = chatAssistant.chat("你好，我的名字是张三");
        System.out.println("ans01 的返回结果是: " + ans01);
        String ans02 = chatAssistant.chat("我的名字是什么？");
        System.out.println("ans02 的返回结果是: " + ans02);
        return "success :" + LocalDateTime.now() + "<br />\n\n ans01: " + ans01 + "<br />\n\n ans02: " + ans02;
    }

    @GetMapping("/chat-memory/message-window")
    public String chatMessageWindow() {
        chatMessageWindowChatMemory.chatWithChatMemory(1L, "你好，我的名字是张三");
        String ans01 = chatMessageWindowChatMemory.chatWithChatMemory(1L, "我的名字是什么？");
        System.out.println("ans01 的返回结果是: " + ans01);

        chatMessageWindowChatMemory.chatWithChatMemory(100L, "你好，我的名字是李四");
        String ans02 = chatMessageWindowChatMemory.chatWithChatMemory(100L, "我的名字是什么？");
        System.out.println("ans02 的返回结果是: " + ans02);
        return "chatMessageWindow success: " + LocalDateTime.now() + "<br />\n\n ans01: " + ans01 + "<br />\n\n ans02: " + ans02;
    }

    @GetMapping("/chat-memory/token-window")
    public String chatTokenWindow() {
        chatTokenWindowChatMemory.chatWithChatMemory(1L, "你好，我的名字是张三");
        String ans01 = chatTokenWindowChatMemory.chatWithChatMemory(1L, "我的名字是什么？");
        System.out.println("ans01 的返回结果是: " + ans01);

        chatTokenWindowChatMemory.chatWithChatMemory(100L, "你好，我的名字是李四");
        String ans02 = chatTokenWindowChatMemory.chatWithChatMemory(100L, "我的名字是什么？");
        System.out.println("ans02 的返回结果是: " + ans02);
        return "chatMessageWindow success: " + LocalDateTime.now() + "<br />\n\n ans01: " + ans01 + "<br />\n\n ans02: " + ans02;
    }
}
