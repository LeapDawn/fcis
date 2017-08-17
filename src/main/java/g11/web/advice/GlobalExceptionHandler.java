package g11.web.advice;

import g11.commons.exception.DataViolationException;
import g11.commons.exception.ExcelException;
import g11.commons.exception.FileException;
import g11.dto.AjaxResult;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public AjaxResult DuplicateKeyExceptionHandler(DuplicateKeyException e) {
        e.printStackTrace();
        return AjaxResult.fail(200, "该记录已经存在");
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public AjaxResult RuntimeExceptionHandler(RuntimeException e) {
        e.printStackTrace();
        return AjaxResult.fail(500, "未知运行异常");
    }

    @ExceptionHandler({CannotCreateTransactionException.class, DataAccessResourceFailureException.class})
    @ResponseBody
    public AjaxResult DataBaseExceptionHandler(RuntimeException e) {
        e.printStackTrace();
        return AjaxResult.fail(300, "数据库连接异常");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public AjaxResult DataIntegrityViolationExceptionHandler(DataIntegrityViolationException e) {
        e.printStackTrace();
        return AjaxResult.fail(400, "数据不合法");
    }

    @ExceptionHandler(DataViolationException.class)
    @ResponseBody
    public AjaxResult DataViolationExceptionHandler(DataViolationException e) {
        return AjaxResult.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(ExcelException.class)
    @ResponseBody
    public AjaxResult ExceptionHandler(ExcelException e) {
        return AjaxResult.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(FileException.class)
    @ResponseBody
    public AjaxResult ExceptionHandler(FileException e) {
        return AjaxResult.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public AjaxResult ExceptionHandler(Exception e) {
        e.printStackTrace();
        return AjaxResult.fail(500, "未知异常");
    }

}
