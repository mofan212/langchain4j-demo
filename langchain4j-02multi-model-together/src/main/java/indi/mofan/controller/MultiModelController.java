package indi.mofan.controller;


import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mofan
 * @date 2025/7/10 12:32
 */
@Slf4j
@RestController
public class MultiModelController {

    @Resource(name = "qwen")
    private ChatModel chatModelQwen;

    @Resource(name = "deepseek")
    private ChatModel chatModelDeepSeek;

    @GetMapping("/multi-model/qwen")
    public String qwenCall(@RequestParam(value = "prompt", defaultValue = "你是谁") String prompt) {
        return chatModelQwen.chat(prompt);
    }

    @GetMapping("/multi-model/deepseek")
    public String deepseekCall(@RequestParam(value = "prompt", defaultValue = "你是谁") String prompt) {
        return chatModelDeepSeek.chat(prompt);
    }
}
