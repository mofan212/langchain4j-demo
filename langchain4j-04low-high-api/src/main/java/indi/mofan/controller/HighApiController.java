package indi.mofan.controller;


import indi.mofan.service.ChatAssistant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mofan
 * @date 2025/7/13 17:09
 */
@RestController
public class HighApiController {
    @Resource
    private ChatAssistant chatAssistant;

    @GetMapping("/highapi/highapi")
    public String highApi(@RequestParam(value = "prompt", defaultValue = "你是谁") String prompt) {
        return chatAssistant.chat(prompt);
    }
}
