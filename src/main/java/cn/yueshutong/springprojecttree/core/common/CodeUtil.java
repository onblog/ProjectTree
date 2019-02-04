package cn.yueshutong.springprojecttree.core.common;


import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.util.Arrays;

/**
 * 构建埋点代码的辅助类
 * Create by yster@foxmail.com 2019/1/31 0031 20:12
 */
public class CodeUtil {

    /**
     * 字符串的代码形式
     * @param s
     * @return
     */
    public static String quotationMark(String s){
        return "\""+s+"\"";
    }

    /**
     * 数组的代码形式
     * @return
     */
    public static String arrayMark(String[] strings){
        StringBuilder builder = new StringBuilder();
        builder.append("new String[]{");
        for (int i = 0; i < strings.length; i++) {
            if (i>0){
                builder.append(",");
            }
            builder.append(quotationMark(strings[i]));
        }
        builder.append("\"\"");
        builder.append("}");
        return builder.toString();
    }

    /**
     * new Date()代码形式
     * @return
     */
    public static String date(){
        return "new java.util.Date()";
    }

    /**
     * 线程Id的代码形式
     * @return
     */
    public static String threadId(){
        return "Thread.currentThread().getId()";
    }

    /**
     * 判断是否是指定范围的类
     * @param name
     * @param trees
     * @return
     */
    public static boolean isAssignClass(String name, String[] trees) {
        for (String s : trees){
            if (s.contains("*")){
                s = s.substring(0,s.indexOf("*"));
                if (name.startsWith(s)){
                    return true;
                }
            }else if (name.equals(s)){
                return true;
            }
        }
        return false;
    }

    /**
     * 检验是不是注解、接口等非class
     * @param cc
     * @return
     */
    public static boolean checkout(CtClass cc) {
        if (cc.isPrimitive()||cc.isAnnotation()||cc.isArray()||cc.isEnum()||cc.isInterface()) {
            return false;
        }
        return true;
    }

    /**
     * 生成方法签名
     *
     * @param cc
     * @param method
     * @return
     */
    public static String genericSignature(CtClass cc, CtMethod method) {
        StringBuilder builder = new StringBuilder();
        builder.append(cc.getName());
        builder.append(".");
        builder.append(method.getName());
        try {
            CtClass[] types = method.getParameterTypes();
            for (CtClass ctClass: types){
                builder.append(".");
                builder.append(ctClass.getName());
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return builder.toString().replaceAll("\\.","x");
    }

    /**
     * 数组去空
     * @param array
     * @return
     */
    public static String[] arrayNotNull(String[] array){
        if (array==null||array.length==0){
            return array;
        }
        return Arrays.stream(array).filter(s -> !"".equals(s)).toArray(String[]::new);
    }
}
