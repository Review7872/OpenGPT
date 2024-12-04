package online.fadai.opengptproject.exception;

public class NoCauseException extends RuntimeException {
    public NoCauseException() {
        super("网络繁忙，请稍后再试");
    }
}
