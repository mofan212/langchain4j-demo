package indi.mofan.controller;


import dev.langchain4j.model.chat.ChatModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mofan
 * @date 2025/7/10 0:05
 */
@Slf4j
@RestController
public class HelloLangChain4JController {

    @Autowired
    private ChatModel chatModel;

    @GetMapping("/langchain4j/hello")
    public String hello(@RequestParam(value = "question", defaultValue = "你是谁") String question) {
        return chatModel.chat(question);
    }
}
