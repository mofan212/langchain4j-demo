package indi.mofan.controller;


import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import indi.mofan.service.ChatAssistant;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author mofan
 * @date 2025/7/14 21:26
 */
@Slf4j
@RestController
public class StreamChatModelController {

    @Resource
    private StreamingChatModel streamingChatModel;
    @Resource
    private ChatAssistant chatAssistant;

    @GetMapping("/chat-stream/chat")
    public void simpleChat(@RequestParam(value = "prompt", defaultValue = "成都有什么吃的？") String prompt) {
        streamingChatModel.chat(prompt, new StreamingChatResponseHandler() {

            @Override
            public void onPartialResponse(String s) {
                System.out.println(s);
            }

            @Override
            public void onCompleteResponse(ChatResponse chatResponse) {
                System.out.println("-- response over: " + chatResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error(throwable.getMessage());
            }
        });
    }

    @GetMapping("/chat-stream/chat-flux")
    public Flux<String> chatFlux(@RequestParam(value = "prompt", defaultValue = "重庆有什么好吃的？") String prompt) {
        return Flux.create(i -> streamingChatModel.chat(prompt, new StreamingChatResponseHandler() {
            @Override
            public void onPartialResponse(String s) {
                i.next(s);
            }

            @Override
            public void onCompleteResponse(ChatResponse chatResponse) {
                i.complete();
            }

            @Override
            public void onError(Throwable throwable) {
                i.error(throwable);
            }
        }));
    }

    @GetMapping("/chat-stream/high-level-chat-flux")
    public Flux<String> highLevelChatFlux(@RequestParam(value = "prompt", defaultValue = "浙江有什么好吃的？") String prompt) {
        return chatAssistant.chatFlux(prompt);
    }
}
