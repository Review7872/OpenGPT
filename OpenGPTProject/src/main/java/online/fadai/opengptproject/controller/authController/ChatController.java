package online.fadai.opengptproject.controller.authController;

import jakarta.annotation.Resource;
import online.fadai.opengptproject.client.OllamaAPI;
import online.fadai.opengptproject.dto.ChatInfoResponse;
import online.fadai.opengptproject.dto.ChatMsgResponse;
import online.fadai.opengptproject.dto.ChatRequest;
import online.fadai.opengptproject.dto.UserInfoRequest;
import online.fadai.opengptproject.enums.ModelType;
import online.fadai.opengptproject.service.TopicService;
import online.fadai.opengptproject.utils.BeanUtils;
import online.fadai.opengptproject.utils.Result;
import online.fadai.opengptproject.utils.ResultUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Resource
    private TopicService topicService;
    @Resource
    private OllamaAPI ollama;

    @PostMapping("/chat")
    public Flux<ChatMsgResponse> chat(ServerWebExchange exchange, @RequestBody ChatRequest chatRequest) {
        ModelType.checkValidModelName(chatRequest.getModelName());
        return ollama.chat(chatRequest.getUuid(), chatRequest.getContent(), chatRequest.getModelName());
    }

    @PostMapping("/newChat")
    public Flux<ChatMsgResponse> newChat(ServerWebExchange exchange, @RequestBody ChatRequest chatRequest) {
        UserInfoRequest userInfo = (UserInfoRequest) exchange.getAttributes().get("userData");
        return topicService.insertMsgTopic(chatRequest.getContent(), ModelType.getModel(chatRequest.getModelName()), userInfo.getUsername());
    }

    @GetMapping("/getChats")
    public Result<List<ChatInfoResponse>> getChats(ServerWebExchange exchange) {
        UserInfoRequest userInfo = (UserInfoRequest) exchange.getAttributes().get("userData");
        return ResultUtil.success(BeanUtils.convertChat(topicService.selectUserTopic(userInfo.getUsername())));
    }

    @PostMapping("/deleteChat")
    public Result<Void> deleteChat(ServerWebExchange exchange, @RequestBody ChatRequest chatRequest) {
        UserInfoRequest userInfo = (UserInfoRequest) exchange.getAttributes().get("userData");
        topicService.updateSee(userInfo.getUsername(), chatRequest.getUuid());
        return ResultUtil.success();
    }
    @GetMapping("/model")
    public Result<List<String>> getModel(ServerWebExchange exchange) {
        return ResultUtil.success(ModelType.getModelNameList());
    }
}
