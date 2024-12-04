package online.fadai.opengptproject.exception;

public class PasswordException extends RuntimeException {
    public PasswordException() {
        super("密码错误");
    }
}
