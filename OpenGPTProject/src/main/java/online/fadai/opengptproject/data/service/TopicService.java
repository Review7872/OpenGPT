package online.fadai.opengptproject.data.service;

import online.fadai.opengptproject.data.pojo.MsgTopic;

import java.sql.Date;
import java.util.List;

public interface TopicService {
    List<MsgTopic> selectUserTopic(String username);
    int insertMsgTopic(String uuid, String topic, String user);
    int updateSee(String uuid);
}
