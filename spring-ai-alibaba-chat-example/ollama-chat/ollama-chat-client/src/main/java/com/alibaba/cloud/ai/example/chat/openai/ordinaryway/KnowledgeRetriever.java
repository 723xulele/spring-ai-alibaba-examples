package com.alibaba.cloud.ai.example.chat.openai.ordinaryway;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class KnowledgeRetriever {
    private List<KnowledgeEntry> knowledgeBase;

    public KnowledgeRetriever(String filePath) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        knowledgeBase = mapper.readValue(new File(filePath),
                mapper.getTypeFactory().constructCollectionType(List.class, KnowledgeEntry.class));
    }

    public String retrieveRelevantInfo(String question) {
        return knowledgeBase.stream()
                .filter(entry -> containsKeywords(question, entry.getContent()))
                .map(KnowledgeEntry::getContent)
                .collect(Collectors.joining("\n"));
    }

    private boolean containsKeywords(String question, String content) {
        String q = question.toLowerCase();
        String c = content.toLowerCase();
        return (q.contains("如何") && c.contains("如何")) ||
                (q.contains("登录") && c.contains("登录")) ||
                (q.contains("报告") && c.contains("报告")) ||
                (q.contains("系统") && c.contains("系统")) ||
                (q.contains("语言") && c.contains("语言"));
    }
}

class KnowledgeEntry {
    private int id;
    private String content;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}