package online.fadai.opengptproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatInfoResponse {
    private String uuid;
    private String topic;
    private String type;
    private String time;
}
