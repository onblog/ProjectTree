package cn.yueshutong.springprojecttree.core.around.util;

import cn.yueshutong.springprojecttree.database.entity.MethodNode;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Create by yster@foxmail.com 2019/2/6 0006 23:34
 */
public class AroundMethodUtil {

    public static MethodNode getMethodNode(ProceedingJoinPoint pjp, int identify,long threadId) {
        String className = pjp.getSignature().getDeclaringTypeName();
        String methodName = pjp.getSignature().getName();
        String methodId = getMethodId(pjp);
        String[] parameters = getParameters(pjp);
        String superclass = getSuperclass(pjp);
        String[] interfaces = getInterfaces(pjp);
        Date startTime = new Date();
        String modifier = getModifier(pjp);
        return new MethodNode(methodId, className, methodName, parameters, modifier, null, threadId, startTime, identify, superclass, interfaces);
    }

    public static String getModifier(ProceedingJoinPoint pjp) {
        int i = pjp.getSignature().getModifiers();
        return Modifier.toString(i);
    }

    public static String[] getInterfaces(ProceedingJoinPoint pjp) {
        Class[] interfaces = pjp.getSignature().getDeclaringType().getInterfaces();
        List<String> interfacesStr = new ArrayList<>();
        if (interfaces != null) {
            Arrays.stream(interfaces).forEach(s -> interfacesStr.add(s.getClass().getName()));
        }
        return interfacesStr.toArray(new String[0]);
    }

    public static String getSuperclass(ProceedingJoinPoint pjp) {
        Class superclass = pjp.getSignature().getDeclaringType().getSuperclass();
        return superclass != null ? superclass.getName() : "superclass null";
    }

    /**
     * 获取参数类型数组
     * @param pjp
     * @return
     */
    public static String[] getParameters(ProceedingJoinPoint pjp) {
        List<String> parameterTypes = new ArrayList<>();
        for (Object object : pjp.getArgs()) {
            parameterTypes.add(object.getClass().getName());
        }
        return parameterTypes.toArray(new String[0]);
    }

    /**
     * 获取MethodId方法签名
     * @param pjp
     * @return
     */
    public static String getMethodId(ProceedingJoinPoint pjp) {
        String className = pjp.getSignature().getDeclaringTypeName();
        String methodName = pjp.getSignature().getName();
        StringBuilder methodId = new StringBuilder();
        methodId.append(className);
        methodId.append("x");
        methodId.append(methodName);
        for (Object object : pjp.getArgs()) {
            methodId.append("x");
            methodId.append(object.getClass().getName());
        }
        return methodId.toString();
    }

}
