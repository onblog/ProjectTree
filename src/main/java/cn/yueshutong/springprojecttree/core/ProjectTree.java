package cn.yueshutong.springprojecttree.core;

import java.util.Optional;

/**
 * 方便记忆的入口方法
 * Create by yster@foxmail.com 2019/2/3 0003 12:28
 */
public class ProjectTree {

    public static void make(String[] basePackage,String[] exclude) {
        ByteCodeAdvice byteCodeAdvice = new ByteCodeAdvice();
        Optional<String[]> basePackage1 = Optional.ofNullable(basePackage);
        Optional<String[]> exclude1 = Optional.ofNullable(exclude);
        byteCodeAdvice.advice(basePackage1.orElse(new String[0]),exclude1.orElse(new String[0]));
    }

    /**
     * 项目中依赖了Devtool热部署插件，必须调用此方法
     * @param basePackage
     * @param exclude
     */
    public static void makeHaveDevtool(String[] basePackage,String[] exclude) {
        if (!Thread.currentThread().getName().equals("main")){
            make(basePackage,exclude);
        }
    }

}
