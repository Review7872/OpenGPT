package online.fadai.opengptproject.repository.mysql.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgTopic {
    private Integer id;
    private String uuid;
    private String topic;
    private Integer type;
    private String user;
    private Integer see;
    private LocalDateTime time;
}
