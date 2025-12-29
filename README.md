# Spring AI Alibaba 项目

这是一个基于 Spring AI 和阿里巴巴 AI 技术栈的示例项目，展示了如何在 Spring Boot 应用中集成和使用各种 AI 能力。

## 技术栈

- **Spring Boot**: 3.5.7
- **Java**: 17
- **Spring AI**: 1.1.0
- **Spring AI Alibaba**: 1.1.0.0-RC1
- **Maven**: 项目构建工具
- **Qdrant**: 向量数据库（用于RAG功能）

## 项目结构

```
spring-ai-alibaba-project/
├── saa-helloworld/          # 基础 Spring AI Alibaba 示例
├── saa-ollama/              # 集成 Ollama 的 Spring AI 示例
├── saa-prompt/              # Spring AI Prompt 模板示例
├── saa-rag/                 # Spring AI RAG (检索增强生成) 示例
├── pom.xml                  # 父项目 Maven 配置
└── README.md                # 项目说明文档
```


## 模块说明

### saa-helloworld
基础的 Spring AI Alibaba 示例项目，展示了如何使用阿里巴巴的 AI 服务。

主要依赖：
- spring-boot-starter-web
- spring-ai-alibaba-starter-dashscope

### saa-ollama
集成了 Ollama 的 Spring AI 示例项目，展示了如何在 Spring 应用中使用本地运行的 Ollama 模型。

主要依赖：
- spring-boot-starter-web
- spring-ai-alibaba-starter-dashscope
- spring-ai-starter-model-ollama

### saa-prompt
Spring AI Prompt 模板示例项目，展示了如何使用 Prompt 模板和系统消息来指导 AI 模型的响应。

主要功能：
- Prompt 模板配置
- 系统消息设置
- 多模型支持（DeepSeek、Qwen）

主要依赖：
- spring-boot-starter-web
- spring-ai-alibaba-starter-dashscope

### saa-rag (重点)
Spring AI RAG (检索增强生成) 示例项目，展示了如何将向量数据库与大语言模型结合，实现基于文档的智能问答。

主要功能：
- 文档向量化存储
- 向量数据库集成 (Qdrant)
- 检索增强生成
- 智能问答系统

主要依赖：
- spring-boot-starter-web
- spring-ai-alibaba-starter-dashscope
- spring-ai-starter-vector-store-qdrant

RAG 实现原理：
1. **文档预处理**：启动时自动读取 `code.md` 文档并分割为片段
2. **向量化**：使用 `text-embedding-v4` 模型将文档片段转换为向量
3. **向量存储**：将向量存储在 Qdrant 向量数据库中
4. **检索增强**：用户提问时，先从向量数据库中检索相关文档片段
5. **生成回答**：将检索到的文档片段作为上下文，让大语言模型生成回答

## 快速开始

### 环境要求

- Java 17 或更高版本
- Maven 3.6.0 或更高版本
- Docker (用于运行 Qdrant 向量数据库，仅 saa-rag 模块需要)

### 准备工作

1. **设置 API 密钥**：
   - 申请阿里云 DashScope API 密钥
   - 配置环境变量：`export AI_DASHSCOPE_API_KEY=your-api-key`

2. **启动 Qdrant 向量数据库** (仅 saa-rag 模块需要)：
   ```bash
   docker run -p 6333:6333 -p 6334:6334 qdrant/qdrant
   ```

### 构建项目

```bash
mvn clean install
```

### 运行模块

#### 运行 saa-helloworld

```bash
cd saa-helloworld
mvn spring-boot:run
```

测试接口：
- http://localhost:8080/hello/chat?msg=你是谁？
- http://localhost:8080/hello/chat/stream?msg=你是谁？

#### 运行 saa-ollama

```bash
cd saa-ollama
mvn spring-boot:run
```

#### 运行 saa-prompt

```bash
cd saa-prompt
mvn spring-boot:run
```

测试接口：
- http://localhost:8080/deepseek/chat?msg=你是谁？
- http://localhost:8080/qwen/chat?msg=什么是法律？
- http://localhost:8080/qwen/chat/stream?msg=什么是法律？

#### 运行 saa-rag

```bash
cd saa-rag
mvn spring-boot:run
```

测试接口：
- http://localhost:8080/rag/chat?msg=0000 Code是什么意思？

## 配置说明

各模块的配置文件位于 `src/main/resources/application.yml`，您需要根据实际情况修改配置，例如 API 密钥、模型参数等。

## API 测试

项目提供了 HTTP 测试文件 `src/test/test.http`，您可以使用支持 HTTP 请求的 IDE（如 IntelliJ IDEA）直接运行测试请求。

## 许可证

[MIT](LICENSE)