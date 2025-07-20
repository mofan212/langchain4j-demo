package indi.mofan.service;


import reactor.core.publisher.Flux;

/**
 * @author mofan
 * @date 2025/7/20 23:00
 */
public interface McpService {
    Flux<String> chat(String question);
}
