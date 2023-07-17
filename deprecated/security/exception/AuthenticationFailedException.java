package deprecated.security.exception;

import lombok.Setter;

/**
 * @author Valinaa
 * @Date : 2022/7/17
 * @Description : 用户验证的自定义异常类
 */
@Setter
public class AuthenticationFailedException extends RuntimeException {
    private int code;
    private String message;
    
    public AuthenticationFailedException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
    
    public int getCode() {
        return code;
    }
}
