package com.ericzzz.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ericzzz.component.TimeTools;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/time")
public class TimeController {
    private final ChatClient dashScopeChatClient;

    private final TimeTools timeTools;

    public TimeController(ChatClient chatClient, TimeTools timeTools) {

        this.dashScopeChatClient = chatClient;
        this.timeTools = timeTools;
    }

    /**
     * 智能问答
     * http://localhost:8080/time/chat?query=请告诉我北京现在几点了？
     * 
     * @param query
     * @return
     */
    @GetMapping("/chat")
    public Flux<String> chat(@RequestParam(value = "query", defaultValue = "请告诉我北京现在几点了？") String query) {
        return dashScopeChatClient.prompt(query)
                .stream().content();
    }

    /**
     * 智能问答，调用方法型工具
     * http://localhost:8080/time/chat-tool-method?query=请告诉我北京现在几点了？
     * 
     * @param query
     * @return
     */
    @GetMapping("/chat-tool-method")
    public Flux<String> chatToolMethod(@RequestParam(value = "query", defaultValue = "请告诉我北京现在几点了？") String query) {
        return dashScopeChatClient.prompt(query).tools(timeTools)
                .stream().content();
    }

}
