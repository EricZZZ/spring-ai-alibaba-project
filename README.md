# Spring AI Alibaba 项目

这是一个基于 Spring AI 和阿里巴巴 AI 技术栈的示例项目，展示了如何在 Spring Boot 应用中集成和使用各种 AI 能力。

## 技术栈

- **Spring Boot**: 3.5.7
- **Java**: 17
- **Spring AI**: 1.1.0
- **Spring AI Alibaba**: 1.1.0.0-RC1
- **Maven**: 项目构建工具

## 项目结构
spring-ai-alibaba-project/ 
├── saa-helloworld/ # 基础 Spring AI Alibaba 示例
├── saa-ollama/ # Ollama 示例
├── pom.xml # 父项目 Maven 配置 
└── README.md # 项目说明文档


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

## 快速开始

### 环境要求

- Java 17 或更高版本
- Maven 3.6.0 或更高版本

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

#### 运行 saa-ollama

```bash
cd saa-ollama
mvn spring-boot:run
```

## 配置说明

各模块的配置文件位于 `src/main/resources/application.yml`，您需要根据实际情况修改配置，例如 API 密钥、模型参数等。

## API 测试

项目提供了 HTTP 测试文件 `src/test/test.http`，您可以使用支持 HTTP 请求的 IDE（如 IntelliJ IDEA）直接运行测试请求。

## 许可证

[MIT](LICENSE)