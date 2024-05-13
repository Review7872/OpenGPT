package online.fadai.opengptproject.data.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgTopic {
    private Integer id;
    private String uuid;
    private String topic;
    private String user;
    private Integer see;
}
