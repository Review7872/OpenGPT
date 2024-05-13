package online.fadai.opengptproject.controller.authController;

import jakarta.annotation.Resource;
import online.fadai.opengptproject.data.elaticsearch.MsgIndexRepository;
import online.fadai.opengptproject.data.pojo.MsgIndex;
import online.fadai.opengptproject.data.pojo.UserMsg;
import org.springframework.ai.chat.messages.ChatMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/llama")
public class AIChatController {
    @Resource
    private OllamaChatClient ollamaChatClient;
    @Resource
    private MsgIndexRepository msgIndexRepository;


    @PostMapping("/chat")
    public String chat(@RequestBody UserMsg msg) {
        List<Message> objects = new ArrayList<>();
        List<MsgIndex> msgs = msgIndexRepository.findByUuid(msg.getUuid());
        msgs.stream().sorted((o1, o2) -> (int) (o1.getTime() - o2.getTime())).forEach(msgIndex -> {
            switch (msgIndex.getRole()) {
                case 1 -> objects.add(new ChatMessage(MessageType.USER, msgIndex.getContent()));
                case 2 -> objects.add(new ChatMessage(MessageType.ASSISTANT, msgIndex.getContent()));
            }
        });
        objects.add(new ChatMessage(MessageType.USER, msg.getContent()));
        String content = ollamaChatClient.call(new Prompt(objects)).getResult().getOutput().getContent();
        msgIndexRepository.save(new MsgIndex(UUID.randomUUID().toString(),msg.getUuid(),1,msg.getContent(),System.currentTimeMillis()));
        msgIndexRepository.save(new MsgIndex(UUID.randomUUID().toString(),msg.getUuid(),2,content,System.currentTimeMillis()));
        return content;
    }
}
