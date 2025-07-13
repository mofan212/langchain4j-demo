package indi.mofan.controller;


import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

/**
 * @author mofan
 * @date 2025/7/13 21:35
 */
@RestController
public class ImageModelController {

    @Resource
    private ChatModel chatModel;

    @Value("classpath:static/images/mi.jpg")
    private org.springframework.core.io.Resource resource;

    @SneakyThrows
    @GetMapping("image/call")
    public String readImageContent() {
        // 1. 通过 Base64 编码将图片转换为字符串
        byte[] byteArray = resource.getContentAsByteArray();
        String base64Data = Base64.getEncoder().encodeToString(byteArray);

        // 2. 指定提示词
        UserMessage userMessage = UserMessage.from(
                TextContent.from("从下面图片中获取来源网站名称、股价走势和 5 月 30 日的股价"),
                ImageContent.from(base64Data, "image/jpg")
        );

        // 3. api 调用
        ChatResponse response = chatModel.chat(userMessage);

        // 4. 解析与输出
        return response.aiMessage().text();
    }
}
