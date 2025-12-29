package com.ericzzz.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

@RestController
public class EmbeddingController {

    @Resource
    private EmbeddingModel embeddingModel;

    @Resource
    private VectorStore vectorStore;

    /**
     * 文本向量化
     * 
     * @param msg
     * @return
     */
    @GetMapping(value = "/text/embedding")
    public EmbeddingResponse textEmbedding(@RequestParam(defaultValue = "测试") String msg) {

        EmbeddingResponse embeddingRequest = embeddingModel.call(new EmbeddingRequest(List.of(msg), null));
        System.out.println(Arrays.toString(embeddingRequest.getResult().getOutput()));
        return embeddingRequest;
    }

    /**
     * 保存文本到向量数据库
     * 
     * @param msg
     * @return
     */
    @GetMapping(value = "/vector/store/add")
    public String vectorStore() {
        List<Document> documents = List.of(
                new Document(
                        "Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!",
                        Map.of("meta1", "meta1")),
                new Document("The World is Big and Salvation Lurks Around the Corner"),
                new Document("You walk forward facing the past and you turn back toward the future.",
                        Map.of("meta2", "meta2")));

        // Add the documents to Qdrant
        vectorStore.add(documents);
        return "success";
    }

    /**
     * 查询向量数据库
     * 
     * @param msg
     * @return
     */
    @GetMapping(value = "/vector/store/search")
    public List<Document> prompt(@RequestParam(defaultValue = "合同法") String query) {
        List<Document> results = vectorStore.similaritySearch(SearchRequest.builder().query("Spring").topK(5).build());

        return results;
    }

}
