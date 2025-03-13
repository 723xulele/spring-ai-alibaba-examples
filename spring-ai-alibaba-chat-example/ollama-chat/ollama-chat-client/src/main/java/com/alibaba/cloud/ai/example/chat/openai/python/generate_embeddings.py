from sentence_transformers import SentenceTransformer
import json
import numpy as np

# 加载模型
model = SentenceTransformer('all-MiniLM-L6-v2')

# 读取知识库（使用绝对路径）
with open(r'F:\Code\Self\spring-ai-alibaba-examples\spring-ai-alibaba-chat-example\ollama-chat\ollama-chat-client\src\main\resources\knowledge\system_knowledge.json', 'r', encoding='utf-8') as f:
    knowledge = json.load(f)

# 生成嵌入
contents = [entry['content'] for entry in knowledge]
embeddings = model.encode(contents)

# 保存嵌入和内容（使用绝对路径）
np.save(r'F:\Code\Self\spring-ai-alibaba-examples\spring-ai-alibaba-chat-example\ollama-chat\ollama-chat-client\src\main\resources\knowledge\embeddings.npy', embeddings)
with open(r'F:\Code\Self\spring-ai-alibaba-examples\spring-ai-alibaba-chat-example\ollama-chat\ollama-chat-client\src\main\resources\knowledge\knowledge_contents.json', 'w', encoding='utf-8') as f:
    json.dump(contents, f, ensure_ascii=False)

print("向量生成完成！")