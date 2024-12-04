package online.fadai.opengptproject.exception;

public class NotUniqueException extends RuntimeException {
    public NotUniqueException() {
        super("用户名或邮箱已经被占用");
    }
}
