package indi.mofan.service;


import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;

/**
 * @author mofan
 * @date 2025/7/19 14:54
 */
public interface ChatPersistenceAssistant {
    String chat(@MemoryId Long memoryId, @UserMessage String message);
}
