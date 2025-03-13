package com.alibaba.cloud.ai.example.chat.openai.ordinaryway;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class SystemChatService {
    private final ChatClient chatClient;
    private KnowledgeRetriever retriever;

    @Autowired
    public SystemChatService(ChatModel chatModel) throws Exception {
        this.chatClient = ChatClient.builder(chatModel)
                // 实现 Chat Memory 的 Advisor
                // 在使用 Chat Memory 时，需要指定对话 ID，以便 Spring AI 处理上下文。
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(new InMemoryChatMemory())
                )
                // 实现 Logger 的 Advisor
                .defaultAdvisors(
                        new SimpleLoggerAdvisor()
                )
                // 设置 ChatClient 中 ChatModel 的 Options 参数
                .defaultOptions(
                        OllamaOptions.builder()
                                .withTopP(0.7)
//								.withModel("deepseek-r1:1.5b")
                                .build()
                )
                .build();
        ClassPathResource resource = new ClassPathResource("knowledge/system_knowledge.json");
        retriever = new KnowledgeRetriever(resource.getFile().getAbsolutePath());
    }

    public String askSystemQuestion(String userQuestion) {
        String relevantInfo = retriever.retrieveRelevantInfo(userQuestion);
        if (relevantInfo.isEmpty()) {
            relevantInfo = "未找到相关信息，请提供更多上下文或检查知识库。";
        }
        String fullPrompt = "以下是我的系统相关信息：\n" +
                relevantInfo +
                "\n请根据上述信息回答用户的问题：\n" +
                userQuestion;
        Prompt prompt = new Prompt(fullPrompt);
        return chatClient.prompt(prompt).call().content();
    }
}
