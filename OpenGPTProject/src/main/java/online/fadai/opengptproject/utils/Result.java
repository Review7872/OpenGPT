package online.fadai.opengptproject.utils;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {

    /**
     * 正确返回码
     */
    public static final String SUCCESS_CODE = "200";
    /**
     * 错误返回码
     */
    public static final String ERROR_CODE = "500";
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 返回码
     */
    private String code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 请求ID
     */
    private String requestId;

    public boolean isSuccess() {
        return SUCCESS_CODE.equals(code);
    }
}

