package indi.mofan.controller;


import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mofan
 * @date 2025/7/13 18:16
 */
@RestController
public class ModelParameterController {
    @Resource
    private ChatModel chatModelQwen;

    @GetMapping("/modelparam/config")
    public String chat(@RequestParam(value = "prompt", defaultValue = "你是谁") String prompt) {
        return chatModelQwen.chat(prompt);
    }
}
