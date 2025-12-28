package com.ericzzz.controller;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import reactor.core.publisher.Flux;

@RestController
public class ChatHelloController {

    @Resource
    private ChatModel deepseekChatModel;

    @Resource
    private ChatClient qwenChatClient;

    /**
     * 调用DeepSeek大模型，返回结果
     * 
     * @param msg
     * @return
     */
    @GetMapping(value = "/deepseek/chat")
    public String chatHello(@RequestParam(defaultValue = "你是谁？") String msg) {

        if (deepseekChatModel == null) {
            return "deepseekChatModel is null";
        }
        return deepseekChatModel.call(msg);
    }

    /**
     * 调用Qwen大模型，返回流式结果
     * 
     * @param msg
     * @return
     */
    @GetMapping(value = "/qwen/chat/stream")
    public Flux<String> stream(@RequestParam(defaultValue = "你是谁？") String msg) {
        return qwenChatClient.prompt(msg).stream().content();
    }

    /**
     * 调用Qwen大模型，返回结果
     * 
     * @param msg
     * @return
     */
    @GetMapping(value = "/qwen/chat")
    public Flux<String> prompt(@RequestParam(defaultValue = "法律问题") String topic,
            @RequestParam(defaultValue = "合同法") String question) {
        // 系统提示模板
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate("你是一个{topic}助手，你只能回答{topic}相关的问题，其他问题请拒绝回答。以HTML格式返回结果。");
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("topic", topic));
        // 用户提示模板
        PromptTemplate userPromptTemplate = new PromptTemplate("解释一下{question}");
        Message userMessage = userPromptTemplate.createMessage(Map.of("question", question));
        // 构建Prompt
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        // 调用Qwen大模型，返回结果
        return qwenChatClient.prompt(prompt).stream().content();
    }

}
