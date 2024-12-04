package online.fadai.opengptproject.service.impl;

import jakarta.annotation.Resource;
import online.fadai.opengptproject.client.OllamaAPI;
import online.fadai.opengptproject.dto.ChatMsgResponse;
import online.fadai.opengptproject.enums.ModelType;
import online.fadai.opengptproject.exception.NoCauseException;
import online.fadai.opengptproject.repository.mysql.dao.TopicDAO;
import online.fadai.opengptproject.repository.mysql.pojo.MsgTopic;
import online.fadai.opengptproject.service.TopicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TopicServiceImpl implements TopicService {
    @Resource
    private TopicDAO topicDAO;
    @Resource
    private OllamaAPI ollama;


    @Override
    public List<MsgTopic> selectUserTopic(String username) {
        return topicDAO.selectUserTopic(username);
    }

    @Override
    public Flux<ChatMsgResponse> insertMsgTopic(String msg, ModelType modelType, String user) {
        String uuid = UUID.randomUUID().toString();
        int i = topicDAO.insertMsgTopic(uuid,
                msg.substring(0, Math.min(msg.length(), 5)), modelType.getModelType(), user, LocalDateTime.now());
        if (i != 1) {
            throw new NoCauseException();
        }
        return ollama.chat(uuid, msg, modelType.getModelName());
    }

    @Override
    public void updateSee(String username, String uuid) {
        int i = topicDAO.updateSee(username, uuid);
        if (i != 1) {
            throw new NoCauseException();
        }
    }
}
