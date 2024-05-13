package online.fadai.opengptproject.data.service;

import online.fadai.opengptproject.data.pojo.UserInfo;

public interface UserService {
    int updatePassword(String email,String password);
    int insertUser(String username, String password, String email);
    int selectUser(String username,String password);
}
