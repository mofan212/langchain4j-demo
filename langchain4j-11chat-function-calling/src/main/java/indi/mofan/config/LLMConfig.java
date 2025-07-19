package indi.mofan.config;


import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolExecutor;
import indi.mofan.service.FunctionAssistant;
import indi.mofan.service.WeatherAssistant;
import indi.mofan.tool.WeatherTools;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author mofan
 * @date 2025/7/19 16:06
 */
@Configuration
public class LLMConfig {
    @Bean
    public ChatModel chatModel() {
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("AliQwen_Key"))
                .modelName("qwen-plus")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
    }

    @Bean
    public FunctionAssistant functionAssistant(ChatModel chatModel) {
        // 工具说明
        ToolSpecification toolSpecification = ToolSpecification.builder()
                .name("开具发票助手")
                .description("根据用户提供的开票信息，开具发票")
                .parameters(
                        JsonObjectSchema.builder()
                                .addStringProperty("companyName", "公司名称")
                                .addStringProperty("dutyNumber", "税号序列")
                                .addStringProperty("amount", "开票金额，保留两位有效数字")
                                .build()
                )
                .build();

        // 业务逻辑
        ToolExecutor toolExecutor = (req, memoryId) -> {
            System.out.println(req.id());
            System.out.println(req.name());
            String args = req.arguments();
            System.out.println("args: " + args);
            return "开具成功";
        };

        return AiServices.builder(FunctionAssistant.class)
                .chatModel(chatModel)
                .tools(Map.of(toolSpecification, toolExecutor))
                .build();
    }

    @Bean
    public WeatherTools weatherService() {
        return new WeatherTools();
    }

    @Bean
    public WeatherAssistant weatherAssistant(ChatModel chatModel, WeatherTools weatherTools) {
        return AiServices.builder(WeatherAssistant.class)
                .chatModel(chatModel)
                .tools(weatherTools)
                .build();
    }
}
