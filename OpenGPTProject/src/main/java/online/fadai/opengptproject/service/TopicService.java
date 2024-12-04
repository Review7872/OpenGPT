package online.fadai.opengptproject.service;


import online.fadai.opengptproject.dto.ChatMsgResponse;
import online.fadai.opengptproject.enums.ModelType;
import online.fadai.opengptproject.repository.mysql.pojo.MsgTopic;
import reactor.core.publisher.Flux;

import java.util.List;

public interface TopicService {
    List<MsgTopic> selectUserTopic(String username);


    Flux<ChatMsgResponse> insertMsgTopic(String msg, ModelType modelType, String user);

    void updateSee(String username, String uuid);
}
