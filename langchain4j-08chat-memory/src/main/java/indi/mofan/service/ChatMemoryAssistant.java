package indi.mofan.service;


import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;

/**
 * @author mofan
 * @date 2025/7/15 18:57
 */
public interface ChatMemoryAssistant {
    String chatWithChatMemory(@MemoryId Long userId, @UserMessage String prompt);
}
