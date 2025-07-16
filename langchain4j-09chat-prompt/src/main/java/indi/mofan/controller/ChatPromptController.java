package indi.mofan.controller;


import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import indi.mofan.entities.LawPrompt;
import indi.mofan.service.LawAssistant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author mofan
 * @date 2025/7/16 12:27
 */
@RestController
public class ChatPromptController {
    @Resource
    private LawAssistant lawAssistant;
    @Resource
    private ChatModel chatModel;

    @GetMapping("/chat-prompt/test1")
    public String test1() {
        String chat1 = lawAssistant.chat("什么是知识产权？", 2000);
        System.out.println(chat1);

        String chat2 = lawAssistant.chat("什么是 Java？", 2000);
        System.out.println(chat2);

        String chat3 = lawAssistant.chat("介绍下西瓜和芒果？", 2000);
        System.out.println(chat3);

        String chat4 = lawAssistant.chat("飞机发动机原理？", 2000);
        System.out.println(chat4);

        return "success : " + LocalDateTime.now() + "<br /> \n\n chat1: " + chat1
               + "<br /> \n\n chat2: " + chat2
               + "<br /> \n\n chat3: " + chat3
               + "<br /> \n\n chat4: " + chat4;
    }

    @GetMapping("/chat-prompt/test2")
    public String test2() {
        LawPrompt lawPrompt = new LawPrompt();
        lawPrompt.setLegal("知识产权");
        lawPrompt.setQuestion("TRIPS协议");

        String chat = lawAssistant.chat(lawPrompt);
        return "success : " + LocalDateTime.now() + "<br /> \n\n chat: " + chat;
    }

    @GetMapping("/chat-prompt/test3")
    public String test3() {
        String role = "外科医生";
        String question = "牙疼";

        PromptTemplate template = PromptTemplate.from("你是一个{{it}}助手，{{question}}怎么办？");
        Prompt prompt = template.apply(Map.of("it", role, "question", question));
        UserMessage userMessage = prompt.toUserMessage();
        ChatResponse response = chatModel.chat(userMessage);
        String text = response.aiMessage().text();
        return "success : " + LocalDateTime.now() + "<br /> \n\n chat: " + text;
    }
}
