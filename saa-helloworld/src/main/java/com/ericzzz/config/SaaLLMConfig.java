package com.ericzzz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;

@Configuration
public class SaaLLMConfig {
    
    // @Value("${spring.ai.dashscope.api-key}")
    // private String apiKey;

    /**
     * 通过YAML配置文件，配置大模型客户端
     * @return
     */
    // @Bean
    // public DashScopeApi chatClient() {
    //     return new DashScopeApi.Builder().apiKey(apiKey).build();
    // }

    /**
     * 通过System环境变量，配置大模型客户端
     * @return
     */
    @Bean
    public DashScopeApi chatClient() {
        return new DashScopeApi.Builder().apiKey(System.getenv("AI_DASHSCOPE_API_KEY")).build();
    }
}
