package indi.mofan.service;


import dev.langchain4j.service.spring.AiService;

/**
 * @author mofan
 * @date 2025/7/10 19:12
 * @link <a href="https://docs.langchain4j.dev/tutorials/spring-boot-integration#spring-boot-starter-for-declarative-ai-services">Spring Boot starter for declarative AI Services</a>
 */
@AiService
public interface ChatAssistant {
    String chat(String userMessage);
}
