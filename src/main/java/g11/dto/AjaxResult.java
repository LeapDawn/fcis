package g11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ajax请求返回结果
 */
@Data
public class AjaxResult {
    private int code;   // 请求是否成功
    private Object value;    // 返回对象

    private AjaxResult() {
    }

    private AjaxResult(int code, Object value) {
        this.code = code;
        this.value = value;
    }

    public static AjaxResult success(Object value) {
        return new AjaxResult(0, value);
    }

    public static AjaxResult fail(int code, Object value) {
        return new AjaxResult(code > 0 ? code : 500, value);
    }
}
