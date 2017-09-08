package g11.commons.exception;

import lombok.Getter;

/**
 * Created by cody on 2017/9/4.
 */
public class NoLoginException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    @Getter
    private int code;

    public NoLoginException(int code, String message) {
        super(message);
        this.code = code;
    }

    public NoLoginException(String message) {
        super(message);
    }
}
