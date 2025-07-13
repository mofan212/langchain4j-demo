package indi.mofan.config;


import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mofan
 * @date 2025/7/13 21:32
 */
@Configuration
public class LLMConfig {

    @Bean(name = "qwen")
    public ChatModel chatModelQwen() {
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("AliQwen_Key"))
                .modelName("qwen-vl-max")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
    }

    /**
     * 使用通义万相实现图片生成
     */
    @Bean
    public WanxImageModel wanxImageModel() {
        return WanxImageModel.builder()
                .apiKey(System.getenv("AliQwen_Key"))
                // 已经指定了使用通义万相，因此不再需要配置 baseUrl
                .modelName("wanx2.1-t2i-turbo")
                .build();
    }

}
