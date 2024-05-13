package online.fadai.opengptproject.data.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMsg {
    private Integer role;
    private String content;
    private Long time;
    private String uuid;
}
