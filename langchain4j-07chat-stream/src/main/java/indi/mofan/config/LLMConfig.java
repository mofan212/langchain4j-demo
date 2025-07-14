package indi.mofan.config;


import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import indi.mofan.service.ChatAssistant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mofan
 * @date 2025/7/14 21:23
 */
@Configuration
public class LLMConfig {

    @Bean
    public StreamingChatModel streamingChatModel() {
        return OpenAiStreamingChatModel.builder()
                .apiKey(System.getenv("AliQwen_Key"))
                .modelName("qwen-plus")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
    }

    @Bean
    public ChatAssistant chatAssistant(StreamingChatModel streamingChatModel) {
        return AiServices.create(ChatAssistant.class, streamingChatModel);
    }
}
