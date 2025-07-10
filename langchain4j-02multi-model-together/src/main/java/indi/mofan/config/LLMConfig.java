package indi.mofan.config;


import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mofan
 * @date 2025/7/10 12:28
 */
@Configuration
public class LLMConfig {

    @Bean(name = "qwen")
    public ChatModel chatModelQwen() {
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("AliQwen_Key"))
                .modelName("qwen-plus")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
    }

    /**
     * <a href="https://platform.deepseek.com/usage">DeepSeek 开放平台</a>
     * <a href="https://api-docs.deepseek.com/zh-cn/">DeepSeek API 文档</a>
     */
    @Bean(name = "deepseek")
    public ChatModel chatModelDeepSeek() {
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("DeepSeek_Key"))
                .modelName("deepseek-chat")
                .baseUrl("https://api.deepseek.com/v1")
                .build();
    }
}
