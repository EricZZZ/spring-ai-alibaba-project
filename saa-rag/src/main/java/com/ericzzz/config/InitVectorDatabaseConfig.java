package com.ericzzz.config;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import jakarta.annotation.PostConstruct;

@Configuration
public class InitVectorDatabaseConfig {
    
    @Autowired
    private VectorStore vectorStore;

    @Value("classpath:code.md")
    private Resource documentResource;


    @PostConstruct
    public void init() {

        // 删除旧数据
        vectorStore.delete("source == 'code.md'");

        // 读取文档内容
        TextReader textReader = new TextReader(documentResource);
        textReader.setCharset(Charset.defaultCharset());

        // 文档转换为向量
        List<Document> documents = new TokenTextSplitter().transform(textReader.read());

        // 向量写入数据库
        vectorStore.write(documents);

    }
}
