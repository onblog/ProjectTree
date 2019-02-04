package cn.yueshutong.springprojecttree.core;

/**
 * 方便记忆的入口方法
 * Create by yster@foxmail.com 2019/2/3 0003 12:28
 */
public class ProjectTree {

    public static void make(String[] basePackage,String[] exclude) {
        ByteCodeAdvice byteCodeAdvice = new ByteCodeAdvice();
        byteCodeAdvice.advice(basePackage,exclude);
    }

}
