package online.fadai.opengptproject.data.service.impl;

import jakarta.annotation.Resource;
import online.fadai.opengptproject.data.mysql.TopicDAO;
import online.fadai.opengptproject.data.pojo.MsgTopic;
import online.fadai.opengptproject.data.service.TopicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
@Transactional
public class TopicServiceImpl implements TopicService {
    @Resource
    private TopicDAO topicDAO;

    @Override
    public List<MsgTopic> selectUserTopic(String username) {
        return topicDAO.selectUserTopic(username);
    }

    @Override
    public int insertMsgTopic(String uuid, String topic, String user) {
        return topicDAO.insertMsgTopic(uuid,topic,user,new Date(System.currentTimeMillis()));
    }

    @Override
    public int updateSee(String uuid) {
        return topicDAO.updateSee(uuid);
    }
}
