package cn.yueshutong.springprojecttree.config.aspect;

import cn.yueshutong.springprojecttree.core.around.AroundMethod;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Random;

public class ProjectTreeAspect {

    public Object around(ProceedingJoinPoint pjp){
        int identify = new Random().nextInt();
        AroundMethod.playBeforeMethod(pjp,identify);
        Object obj = null;
        try {
            obj = pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        AroundMethod.playAfterMethod(pjp,identify,obj);
        return obj;
    }

}
