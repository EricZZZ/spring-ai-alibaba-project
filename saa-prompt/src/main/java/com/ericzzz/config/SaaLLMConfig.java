package com.ericzzz.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;

@Configuration
public class SaaLLMConfig {

    @Value("${spring.ai.dashscope.api-key}")
    private String apiKey;

    private final static String QWEN_PLUS = "qwen-plus";
    private final static String DEEPSEEK = "deepseek-v3.2";

    /**
     * 通过YAML配置文件，配置大模型客户端
     * 
     * @return
     */
    @Bean
    public ChatModel deepseekChatModel() {

        // 创建 DashScope API 实例
        DashScopeApi dashScopeApi = DashScopeApi.builder()
                .apiKey(apiKey)
                .build();

        ChatModel chatModel = DashScopeChatModel.builder()
                .dashScopeApi(dashScopeApi)
                .defaultOptions(DashScopeChatOptions.builder().model(DEEPSEEK).build()).build();
        return chatModel;
    }

    @Bean
    public ChatClient qwenChatClient() {
        // 创建 DashScope API 实例
        DashScopeApi dashScopeApi = DashScopeApi.builder()
                .apiKey(apiKey)
                .build();

        ChatModel chatModel = DashScopeChatModel.builder()
                .dashScopeApi(dashScopeApi)
                .defaultOptions(DashScopeChatOptions.builder().model(QWEN_PLUS).build()).build();
        
        return ChatClient.builder(chatModel).build();
    }

}
