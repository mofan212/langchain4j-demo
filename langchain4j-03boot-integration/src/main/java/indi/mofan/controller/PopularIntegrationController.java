package indi.mofan.controller;


import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mofan
 * @date 2025/7/10 19:00
 */
@RestController
public class PopularIntegrationController {
    @Resource
    private ChatModel chatModel;

    @GetMapping("/lc4j/boot/chat")
    public String chat(@RequestParam(value = "prompt", defaultValue = "你是谁") String prompt) {
        return chatModel.chat(prompt);
    }
}
