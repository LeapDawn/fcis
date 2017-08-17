package g11.commons.exception;

import lombok.Getter;

/**
 * Excel操作异常 
 */
public class ExcelException extends Exception{

	private static final long serialVersionUID = 1L;

	@Getter
	private int code;

	public ExcelException(int code, String message) {
		super(message);
		this.code = code;
	}

    public ExcelException(String message) {
        super(message);
    }
}
