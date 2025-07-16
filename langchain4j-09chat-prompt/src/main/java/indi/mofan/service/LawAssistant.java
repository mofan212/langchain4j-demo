package indi.mofan.service;


import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import indi.mofan.entities.LawPrompt;

/**
 * @author mofan
 * @date 2025/7/16 12:20
 */
public interface LawAssistant {
    @SystemMessage("""
            你是一位专业的中国法律顾问，只回答与中国法律相关的问题。
            输出限制：对于其他领域的问题禁止回答，直接返回'抱歉，我只能回答中国法律相关的问题'。
            """)
    @UserMessage("请回答以下法律问题：{{question}}，字数控制在 {{length}} 以内。")
    String chat(@V("question") String question, @V("length") int length);

    @SystemMessage("""
            你是一位专业的中国法律顾问，只回答与中国法律相关的问题。
            输出限制：对于其他领域的问题禁止回答，直接返回'抱歉，我只能回答中国法律相关的问题'。
            """)
    String chat(LawPrompt lawPrompt);
}
