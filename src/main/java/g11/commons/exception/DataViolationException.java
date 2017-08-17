package g11.commons.exception;

import lombok.Getter;
import org.springframework.dao.DataIntegrityViolationException;

public class DataViolationException extends DataIntegrityViolationException {

    private static final long serialVersionUID = 1L;

    @Getter
    private int code;

    public DataViolationException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}
