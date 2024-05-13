package online.fadai.opengptproject.data.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private Integer id;
    private String username;
    private String password;
    private String email;
}
