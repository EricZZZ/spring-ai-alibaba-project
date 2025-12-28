package com.ericzzz.controller;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import reactor.core.publisher.Flux;

@RestController
public class ChatHelloController {
    
    @Resource
    private ChatModel chatModel;

    /**
     * 调用大模型，返回结果
     * @param msg
     * @return
     */
    @GetMapping(value = "/hello/chat")
    public String chatHello(@RequestParam(name ="msg",defaultValue ="你是谁？") String msg) {
        
        if (chatModel == null) {
            return "chatModel is null";
        }
        return chatModel.call(msg);
    }   

    /**
     * 调用大模型，返回流式结果
     * @param msg
     * @return
     */
    @GetMapping(value = "/hello/chat/stream")
    public Flux<String> stream(@RequestParam(name ="msg",defaultValue ="你是谁？") String msg) {
        return chatModel.stream(msg);
    }

}
