package indi.mofan.config;


import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mofan
 * @date 2025/7/10 0:00
 */
@Configuration
public class LLMConfig {
    @Bean
    public ChatModel chatModelQwen() {
        return OpenAiChatModel.builder()
                // 从环境变量中获取配置的 API-Key（配置环境变量后，记得重启 IDEA，否则可能获取不到！）
                .apiKey(System.getenv("AliQwen_Key"))
                .modelName("qwen-plus")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
    }
}
