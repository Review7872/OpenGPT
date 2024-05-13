package online.fadai.opengptproject.llmapi;

import jakarta.annotation.Resource;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.stereotype.Component;

@Component
public class OllamaAPI {
    @Resource
    private OllamaChatClient ollamaChatClient;

}
