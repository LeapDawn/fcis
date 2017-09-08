package g11.web.advice;

import g11.commons.exception.NoLoginException;
import g11.commons.util.AccessUtil;
import g11.dto.AjaxResult;
import g11.model.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Aspect
@Component
public class GlobalUserAop {

    @Autowired
    private HttpSession session;

//    @Pointcut("execution(* g11.web.*.*(..)) && !execution(* g11.web.UserController.login(..))")
//    public void loginService() {
//
//    }

    @Pointcut("execution(* g11.web.A*Controller.save*(..)) " +
            "|| execution(* g11.web.A*Controller.add*(..)) " +
            "|| execution(* g11.web.A*Controller.upload*(..))")
    public void inputService() {

    }

    @Pointcut("execution(* g11.web.Advanced*Controller.*tatics*(..)) " +
            "|| execution(* g11.web.AdvancedPersonController.overdue(..)) " +
            "|| execution(* g11.web.AdvancedPersonController.recognise(..))")
    public void specialQueryService() {

    }

    @Pointcut("(execution(* g11.web.Data*Controller.*(..)) || execution(* g11.web.UserController.*(..)))" +
            "&& !(execution(* g11.web.DataDirController.list*(..)) || execution(* g11.web.UserController.log*(..))) " +
            "|| execution(* g11.web.UserController.updatePassword(..)))")
    public void systemManagerService() {

    }

    @Pointcut("execution(* g11.web.A*Controller.list*(..)) || execution(* g11.web.DataDirController.list*(..)) ")
    public void queryService() {

    }

    @Around("inputService()")
    public Object inputValidate(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        User user = loginValidate();
        if (user == null) {
            return AjaxResult.fail(-1,"");
        }
        if (AccessUtil.isInput(user.getFunction())) {
            return thisJoinPoint.proceed(thisJoinPoint.getArgs());
        }
        return AjaxResult.fail(812, "没有录入权限");
    }

    @Around("specialQueryService()")
    public Object specialQueryValidate(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        User user = loginValidate();
        if (user == null) {
            return AjaxResult.fail(-1,"");
        }
        if (AccessUtil.isSpecialQuery(user.getFunction())) {
            return thisJoinPoint.proceed(thisJoinPoint.getArgs());
        }
        return AjaxResult.fail(813, "没有特殊信息查询权限");
    }

    @Around("systemManagerService()")
    public Object systemManagerValidate(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        User user = loginValidate();
        if (user == null) {
            return AjaxResult.fail(-1,"");
        }
        if (AccessUtil.isSystemManager(user.getFunction())) {
            return thisJoinPoint.proceed(thisJoinPoint.getArgs());
        }
        return AjaxResult.fail(814, "没有管理员权限");
    }

    @Around("queryService()")
    public Object queryValidate(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        User user = loginValidate();
        if (user != null) {
            return thisJoinPoint.proceed(thisJoinPoint.getArgs());
        } else {
            return AjaxResult.fail(-1,"");
        }
    }

    private User loginValidate() {
        if (session != null) {
            Object user = session.getAttribute("user");
            System.out.println("user:  " + user);
//            if (user == null) {
//                throw new NoLoginException(-1, "");
//            } else {
//                return (User) user;
//            }
            return (User) user;
        } else {
            throw new NoLoginException(811, "");
        }
    }
}
