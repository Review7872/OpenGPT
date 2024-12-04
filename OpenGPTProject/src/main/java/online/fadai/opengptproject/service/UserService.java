package online.fadai.opengptproject.service;

public interface UserService {
    void updatePassword(String email, String password);

    void insertUser(String username, String password, String email);

    String selectUser(String username, String password);
}
