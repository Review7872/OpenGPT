package online.fadai.opengptproject.utils;

import online.fadai.opengptproject.dto.ChatInfoResponse;
import online.fadai.opengptproject.enums.ModelType;
import online.fadai.opengptproject.enums.RoleType;
import online.fadai.opengptproject.repository.es.pojo.MsgIndex;
import online.fadai.opengptproject.repository.mysql.pojo.MsgTopic;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BeanUtils {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Message convert(MsgIndex msg) {
        if (Objects.equals(msg.getRole(), RoleType.USER.getRoleType())) {
            return new UserMessage(msg.getContent());
        } else if (Objects.equals(msg.getRole(), RoleType.Assistant.getRoleType())) {
            return new AssistantMessage(msg.getContent());
        }
        return null;
    }

    public static List<Message> convert(List<MsgIndex> msgs) {
        List<Message> list = new ArrayList<>();
        msgs.forEach(msg -> list.add(convert(msg)));
        return list;
    }

    public static ChatInfoResponse convertChat(MsgTopic msgTopic) {
        return new ChatInfoResponse(msgTopic.getUuid(), msgTopic.getTopic(), ModelType.getModelName(msgTopic.getType()), FORMATTER.format(msgTopic.getTime()));
    }

    public static List<ChatInfoResponse> convertChat(List<MsgTopic> msgTopics) {
        List<ChatInfoResponse> list = new ArrayList<>();
        msgTopics.forEach(msgTopic -> list.add(convertChat(msgTopic)));
        return list;
    }
}
