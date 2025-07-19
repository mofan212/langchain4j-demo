package indi.mofan.controller;


import indi.mofan.service.FunctionAssistant;
import indi.mofan.service.WeatherAssistant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author mofan
 * @date 2025/7/19 16:21
 */
@RestController
public class ChatFunctionCallingController {
    @Resource
    private FunctionAssistant functionAssistant;
    @Resource
    private WeatherAssistant weatherAssistant;

    @GetMapping("/chat-function/test")
    public String test() {
        String chat = functionAssistant.chat("开张发票，公司：啥都有小卖部 税号：everything666 金额：888");
        System.out.println(chat);
        return "success: " + LocalDateTime.now() + "<br />" + chat;
    }

    @GetMapping("/chat-function/weather")
    public String getWeather() {
        return weatherAssistant.chat("今天成都的天气怎么样？");
    }
}
