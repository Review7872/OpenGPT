package online.fadai.opengptproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMsgResponse {
    private String uuid;
    private String msg;
}
