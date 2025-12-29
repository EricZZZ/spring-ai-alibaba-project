package com.ericzzz.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import reactor.core.publisher.Flux;

@RestController
public class RagController {

    @Resource
    private EmbeddingModel embeddingModel;

    @Resource
    private VectorStore vectorStore;

    @Resource
    private ChatClient qwenChatClient;

    /**
     * RAG 智能问答
     * http://localhost:8080/rag/chat?msg=0000
     * 
     * @param msg
     * @return
     */
    @GetMapping(value = "/rag/chat")
    public Flux<String> rag(@RequestParam(defaultValue = "0000 Code是什么意思？") String msg) {

        String systemInfo = """
                你是一个运维工程师，按照给出的编码给出对应的故障解释，否则回复找不到信息。
                """;

        RetrievalAugmentationAdvisor advisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(VectorStoreDocumentRetriever.builder().vectorStore(vectorStore).build()).build();
        return qwenChatClient.prompt()
                .user(msg)
                .system(systemInfo).advisors(advisor)
                .stream()
                .content();
    }

}
