package online.fadai.opengptproject.exception;

public class ModelInvalidException extends RuntimeException {
    public ModelInvalidException() {
        super("模型名称无效");
    }
}
