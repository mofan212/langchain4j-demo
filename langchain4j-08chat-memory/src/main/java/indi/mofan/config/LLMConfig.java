package indi.mofan.config;


import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiTokenCountEstimator;
import dev.langchain4j.service.AiServices;
import indi.mofan.service.ChatAssistant;
import indi.mofan.service.ChatMemoryAssistant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mofan
 * @date 2025/7/15 20:08
 */
@Configuration
public class LLMConfig {

    @Bean
    public ChatModel streamingChatModel() {
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("AliQwen_Key"))
                .modelName("qwen-long")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
    }

    @Bean(name = "chat")
    public ChatAssistant chatAssistant(ChatModel chatModel) {
        return AiServices.create(ChatAssistant.class, chatModel);
    }

    @Bean(name = "chatMessageWindowChatMemory")
    public ChatMemoryAssistant chatMessageWindowChatMemory(ChatModel chatModel) {
        return AiServices.builder(ChatMemoryAssistant.class)
                .chatModel(chatModel)
                .chatMemoryProvider(i -> MessageWindowChatMemory.builder()
                        .id(i)
                        .maxMessages(100)
                        .build())
                .build();
    }

    @Bean(name = "chatTokenWindowChatMemory")
    public ChatMemoryAssistant chatTokenWindowChatMemory(ChatModel chatModel) {
        return AiServices.builder(ChatMemoryAssistant.class)
                .chatModel(chatModel)
                .chatMemoryProvider(i -> TokenWindowChatMemory.builder()
                        .id(i)
                        .maxTokens(1000, new OpenAiTokenCountEstimator("gpt-4"))
                        .build())
                .build();
    }

}
