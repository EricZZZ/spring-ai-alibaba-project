package com.ericzzz.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass; // 添加这行导入
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.cloud.ai.toolcalling.baidumap.BaiduMapSearchInfoService;
import com.alibaba.cloud.ai.toolcalling.time.GetTimeByZoneIdService;
import com.ericzzz.component.AddressInformationTools;
import com.ericzzz.component.TimeTools;

@Configuration
@ConditionalOnClass(GetTimeByZoneIdService.class)
public class ToolCallAutoConfiguration {
     @Bean
    public AddressInformationTools addressInformationTools(BaiduMapSearchInfoService service) {
        return new AddressInformationTools(service);
    }

    @Bean
    public TimeTools timeTools(GetTimeByZoneIdService service) {
        return new TimeTools(service);
    }

    @Bean
    public ChatClient chatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }
}
