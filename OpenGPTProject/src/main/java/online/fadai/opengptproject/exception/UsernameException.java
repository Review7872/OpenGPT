package online.fadai.opengptproject.exception;

public class UsernameException extends RuntimeException {
    public UsernameException() {
        super("用户名不存在");
    }
}
