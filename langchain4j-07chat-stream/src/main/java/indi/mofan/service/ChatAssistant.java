package indi.mofan.service;


import reactor.core.publisher.Flux;

/**
 * @author mofan
 * @date 2025/7/14 21:22
 */
public interface ChatAssistant {
    Flux<String> chatFlux(String prompt);
}
