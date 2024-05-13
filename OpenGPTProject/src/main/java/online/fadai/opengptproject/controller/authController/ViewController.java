package online.fadai.opengptproject.controller.authController;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import online.fadai.opengptproject.data.elaticsearch.MsgIndexRepository;
import online.fadai.opengptproject.data.pojo.MsgIndex;
import online.fadai.opengptproject.data.pojo.MsgTopic;
import online.fadai.opengptproject.data.service.TopicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class ViewController {
    @Resource
    private TopicService topicService;
    @Resource
    private AIChatController aiChatController;
    @Resource
    private MsgIndexRepository msgIndexRepository;
    @GetMapping("/main")
    public String mainView(HttpSession httpSession, Model model){
        String user = (String) httpSession.getAttribute("user");
        List<MsgTopic> msgTopics = topicService.selectUserTopic(user);
        model.addAttribute("msgTopics",msgTopics);
        return "topicView";
    }
    @GetMapping("/chat")
    public String chat(String uuid,Model model){
        List<MsgIndex> msgs = msgIndexRepository.findByUuid(uuid);
        List<MsgIndex> list = msgs.stream().sorted((o1, o2) -> (int) (o1.getTime() - o2.getTime())).toList();
        model.addAttribute("msgTopic",list);
        model.addAttribute("uuid",uuid);
        return "chatView";
    }
    @GetMapping("/deleteTopic")
    public String deleteTopic(String uuid){
        topicService.updateSee(uuid);
        return "redirect:/main";
    }
    @GetMapping("/newTopic")
    public String newTopic(String topicName,HttpSession httpSession){
        String uuid = UUID.randomUUID().toString();
        topicService.insertMsgTopic(uuid,topicName,(String) httpSession.getAttribute("user"));
        return "redirect:/main";
    }
}
