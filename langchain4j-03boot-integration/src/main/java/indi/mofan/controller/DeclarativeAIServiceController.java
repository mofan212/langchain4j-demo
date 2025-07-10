package indi.mofan.controller;


import indi.mofan.service.ChatAssistant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mofan
 * @date 2025/7/10 19:33
 */
@RestController
public class DeclarativeAIServiceController {
    @Resource
    private ChatAssistant chatAssistant;

    @GetMapping("/lc4j/boot/declarative")
    public String declarative(@RequestParam(value = "prompt", defaultValue = "who are you?") String prompt) {
        return chatAssistant.chat(prompt);
    }
}
