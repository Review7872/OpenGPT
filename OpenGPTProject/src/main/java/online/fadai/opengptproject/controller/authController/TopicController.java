package online.fadai.opengptproject.controller.authController;

import jakarta.annotation.Resource;
import online.fadai.opengptproject.data.elaticsearch.MsgIndexRepository;
import online.fadai.opengptproject.data.pojo.MsgIndex;
import online.fadai.opengptproject.data.pojo.MsgTopic;
import online.fadai.opengptproject.data.service.TopicService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/topic")
public class TopicController {
    @Resource
    private TopicService topicService;
    @Resource
    private MsgIndexRepository msgIndexRepository;

    @PostMapping("/insert")
    public int insertTopic(@RequestBody MsgTopic msgTopic) {
        return topicService.insertMsgTopic(msgTopic.getUuid(),
                msgTopic.getTopic().substring(0, Math.min(msgTopic.getTopic().length(), 250)),
                msgTopic.getUser());
    }

    @PostMapping("/selectUserTopic")
    public Object selectUserTopic(@RequestBody MsgTopic msgTopic) {
        return topicService.selectUserTopic(msgTopic.getUser());
    }

    @PostMapping("/updateSee")
    public int updateSee(@RequestBody MsgTopic msgTopic) {
        return topicService.updateSee(msgTopic.getUuid());
    }

    @PostMapping("/selectTopic")
    public List<MsgIndex> selectTopic(@RequestBody MsgTopic msgTopic) {
        List<MsgIndex> msgs = msgIndexRepository.findByUuid(msgTopic.getUuid());
        return msgs.stream().sorted((o1, o2) -> (int) (o1.getTime() - o2.getTime())).toList();
    }
}
