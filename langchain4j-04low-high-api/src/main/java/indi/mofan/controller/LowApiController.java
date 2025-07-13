package indi.mofan.controller;


import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.output.TokenUsage;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mofan
 * @date 2025/7/13 16:38
 */
@RestController
public class LowApiController {

    @Resource(name = "qwen")
    private ChatModel chatModelQwen;

    @Resource(name = "deepseek")
    private ChatModel chatModelDeepSeek;

    @GetMapping("/lowapi/api01")
    public String chat(@RequestParam(value = "prompt", defaultValue = "你是谁") String prompt) {
        return chatModelQwen.chat(prompt);
    }

    /**
     * Token 用量计算的底层 API 演示验证案例
     */
    @GetMapping("/lowapi/api02")
    public String getTokenUsage(@RequestParam(value = "prompt", defaultValue = "你是谁") String prompt) {
        ChatResponse response = chatModelDeepSeek.chat(UserMessage.from(prompt));
        String result = response.aiMessage().text();
        System.out.println("调用大模型的返回结果: " + result);
        TokenUsage tokenUsage = response.tokenUsage();
        System.out.println("本次调用消耗的 token: " + tokenUsage);
        return result + "\t\n" + tokenUsage;
    }
}
