package indi.mofan.listener;


import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author mofan
 * @date 2025/7/13 18:37
 */
@Slf4j
public class TestChatModelListener implements ChatModelListener {
    @Override
    public void onRequest(ChatModelRequestContext requestContext) {
        String uuid = UUID.randomUUID().toString();
        requestContext.attributes().put("TraceId", uuid);
        log.info("请求参数 requestContext: {}", requestContext + "\t" + uuid);
    }

    @Override
    public void onResponse(ChatModelResponseContext responseContext) {
        Object traceId = responseContext.attributes().get("TraceId");
        log.info("返回结果 responseContext: {}", responseContext + "\t" + traceId);
    }

    @Override
    public void onError(ChatModelErrorContext errorContext) {
        log.error("请求异常 errorContext: {}", errorContext);
    }
}
