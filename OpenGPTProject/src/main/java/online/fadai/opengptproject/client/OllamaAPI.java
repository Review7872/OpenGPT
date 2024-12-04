package online.fadai.opengptproject.client;

import jakarta.annotation.Resource;
import online.fadai.opengptproject.dto.ChatMsgResponse;
import online.fadai.opengptproject.enums.RoleType;
import online.fadai.opengptproject.repository.es.pojo.MsgIndex;
import online.fadai.opengptproject.service.MsgService;
import online.fadai.opengptproject.utils.BeanUtils;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class OllamaAPI {
    @Resource
    private OllamaChatClient ollamaChatClient;
    @Resource
    private MsgService msgService;

    public Flux<ChatMsgResponse> chat(String uuid, String msg, String model) {
        ChatOptions chatOptions = OllamaOptions.create().withModel(model);
        List<MsgIndex> msgs = Optional.ofNullable(msgService.findByUuid(uuid)).orElseGet(ArrayList::new);
        msgs.add(new MsgIndex(UUID.randomUUID().toString(), uuid, RoleType.USER.getRoleType(),
                msg, System.currentTimeMillis()));
        List<Message> messages = BeanUtils.convert(msgs);
        Flux<ChatResponse> stream = ollamaChatClient.stream(new Prompt(messages, chatOptions));
        StringBuilder aiMsg = new StringBuilder();
        return stream.map(chatResponse -> {
                    String content = chatResponse.getResult().getOutput().getContent();
                    return new ChatMsgResponse(uuid, content.replace("\n", ""));
                })
                .doOnNext(chatMsgResponse -> aiMsg.append(chatMsgResponse.getMsg()))
                .doOnTerminate(() -> {
                    msgService.save(uuid, RoleType.USER, msg);
                    msgService.save(uuid, RoleType.Assistant, aiMsg.toString());
                });
    }
}