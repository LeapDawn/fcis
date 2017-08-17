package g11.web.advice;

import g11.dto.AjaxResult;
import g11.model.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Aspect
@Component
public class GlobalUserAop {

    @Autowired
    private HttpSession session;

    @Pointcut("execution(* g11.web.*.*(..)) && !execution(* g11.web.UserController.login(..))")
    public void loginService() {

    }

    @Pointcut("execution(* g11.web.*.*(..)) && !execution(* g11.web.UserController.login(..))")
    public void inputService() {

    }

    @Pointcut("execution(* g11.web.*.*(..)) && !execution(* g11.web.UserController.login(..))")
    public void queryService() {

    }

    @Pointcut("execution(* g11.web.*.*(..)) && !execution(* g11.web.UserController.login(..))")
    public void systemManagerService() {

    }

    @Around("loginService()")
    public Object loginValidate(ProceedingJoinPoint thisJoinPoint){
        System.out.println("进入GlobalUserAop.loginValidate()");
        System.out.println("========================================");
        if (session != null) {
            Object user = session.getAttribute("user");
            System.out.println("user:  " +  user);
            if (user == null) {
                return AjaxResult.fail(901,"未登录");
            } else {
                try {
                    return thisJoinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
        return AjaxResult.fail(500,"");
    }

    @Around("inputService()")
    public Object inputValidate(ProceedingJoinPoint thisJoinPoint){
        return AjaxResult.fail(500,"");
    }

    @Around("queryService()")
    public Object queryValidate(ProceedingJoinPoint thisJoinPoint){
        return AjaxResult.fail(500,"");
    }

    @Around("systemManagerService()")
    public Object systemManagerValidate(ProceedingJoinPoint thisJoinPoint){
        return AjaxResult.fail(500,"");
    }
}
