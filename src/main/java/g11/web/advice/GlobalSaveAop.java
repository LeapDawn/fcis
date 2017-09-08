package g11.web.advice;

import g11.dto.AjaxResult;
import g11.model.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;

@Aspect
@Component
public class GlobalSaveAop {

    @Autowired
    private HttpSession session;

    //只是切入点，方法里面不能写东西
    @Pointcut("execution(* g11.web.*Controller.save*(..)) || execution(* g11.web.*Controller.add*(..))")
    public void savePointCut() {

    }

    @Pointcut("execution(* g11.web.*Controller.update*(..))")
    public void updatePointCut() {

    }

    //"savePointCut()"指定切入点
    @Around("savePointCut()")
    //JSON字段全为空字符串或null，返回错误，否则放行
    public Object saveValidate(ProceedingJoinPoint thisJoinPoint){
        //获得原本传给controller的参数
        System.out.print("进入save通知");
        Object[] objects = thisJoinPoint.getArgs();
        Field[] fields = objects[0].getClass().getDeclaredFields();
        try{
            for(Field field : fields){
                field.setAccessible(true);
                if (field.get(objects[0]) != null){
                    if (field.getType() == String.class){
                        if (!"".equals(field.get(objects[0]))){
                            return thisJoinPoint.proceed();
                        }
                        else {

                        }
                    }
                    else{
                        return thisJoinPoint.proceed();
                    }
                }
            }
        }
        catch (Throwable throwable){
            throwable.printStackTrace();
        }

        return AjaxResult.fail(500,"新增信息不能全部为空");
    }

    @Around("updatePointCut()")
    //JSON字段，除了id外，全为空字符串或null，返回错误，否则放行
    public Object updateValidate(ProceedingJoinPoint thisJoinPoint){
        //获得原本传给controller的参数
        System.out.print("进入update通知");
        Object[] objects = thisJoinPoint.getArgs();
        Field[] fields = objects[0].getClass().getDeclaredFields();
        try{
            for(Field field : fields){
                field.setAccessible(true);
                if (! "id".equals(field.getName())){
                    if (field.get(objects[0]) != null){
                        if (field.getType() == String.class){
                            if (!"".equals(field.get(objects[0]))){
                                return thisJoinPoint.proceed();
                            }
                            else {

                            }
                        }
                        else{
                            return thisJoinPoint.proceed();
                        }
                    }
                }
                else{

                }
            }
        }
        catch (Throwable throwable){
            throwable.printStackTrace();
        }

        return AjaxResult.fail(500,"修改信息不能全部为空");
    }
}
