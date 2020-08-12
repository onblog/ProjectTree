package com.github.onblog.springprojecttree.core.around;

import com.github.onblog.springprojecttree.core.point.BuriedPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create by yster@foxmail.com 2019/2/6 0006 23:31
 */
public class AroundMethod {
    private static Logger logger = LoggerFactory.getLogger(AroundMethod.class);

    public static void playBeforeMethod(ProceedingJoinPoint pjp, int identify) {
        logger.debug("AroundMethodBefore "+pjp.getSignature().getDeclaringTypeName()+" "+pjp.getSignature().getName());
        BuriedPoint.before(pjp,identify);
    }

    public static void playAfterMethod(ProceedingJoinPoint pjp, int identify, Object obj) {
        logger.debug("AroundMethodAfter "+pjp.getSignature().getDeclaringTypeName()+" "+pjp.getSignature().getName());
        BuriedPoint.after(identify,obj!=null?obj.getClass().getName():null);
    }

}