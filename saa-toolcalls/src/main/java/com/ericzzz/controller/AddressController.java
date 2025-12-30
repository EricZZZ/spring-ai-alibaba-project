package com.ericzzz.controller;

import java.lang.reflect.Method;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.ai.util.json.schema.JsonSchemaGenerator;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.cloud.ai.toolcalling.baidumap.BaiduMapSearchInfoService;
import com.ericzzz.component.AddressInformationTools;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/address")
public class AddressController {

        private final ChatClient dashScopeChatClient;
        private final AddressInformationTools addressTools;

        public AddressController(ChatClient chatClient, AddressInformationTools addressTools) {
                this.dashScopeChatClient = chatClient;
                this.addressTools = addressTools;
        }

        /**
         * 智能问答
         * http://localhost:8080/address/chat?address=北京
         * 
         * @param address
         * @return
         */
        @GetMapping("/chat")
        public Flux<String> chat(@RequestParam(value = "address", defaultValue = "北京") String address)
                        throws JsonProcessingException {

                BaiduMapSearchInfoService.Request query = new BaiduMapSearchInfoService.Request(address);

                return dashScopeChatClient.prompt(new ObjectMapper().writeValueAsString(query))
                                .stream().content();
        }

        /**
         * 智能问答，调用方法型工具
         * http://localhost:8080/address/chat-method-tool-callback?address=北京
         * 
         * @param address
         * @return
         * @throws JsonProcessingException
         */
        @GetMapping("/chat-method-tool-callback")
        public Flux<String> chatWithBaiduMap(@RequestParam(value = "address", defaultValue = "北京") String address)
                        throws JsonProcessingException {

                Method method = ReflectionUtils.findMethod(AddressInformationTools.class, "getAddressInformation",
                                String.class);

                if (method == null) {
                        throw new RuntimeException("Method not found");
                }

                return dashScopeChatClient.prompt(address)
                                .toolCallbacks(MethodToolCallback.builder()
                                                .toolDefinition(ToolDefinition.builder()
                                                                .description("Search for places using Baidu Maps API "
                                                                                + "or Get detail information of a address and facility query with baidu map or "
                                                                                + "Get address information of a place with baidu map or "
                                                                                + "Get detailed information about a specific place with baidu map")
                                                                .name("getAddressInformation")
                                                                .inputSchema(JsonSchemaGenerator
                                                                                .generateForMethodInput(method))
                                                                .build())
                                                .toolMethod(method)
                                                .toolObject(addressTools)
                                                .build())
                                .stream().content();
        }

}
