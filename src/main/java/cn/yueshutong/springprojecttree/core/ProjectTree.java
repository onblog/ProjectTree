package cn.yueshutong.springprojecttree.core;

import cn.yueshutong.springprojecttree.core.aspect.AutoCreateAspect;

import java.util.Optional;

/**
 * Create by yster@foxmail.com 2019/2/7 0007 14:27
 */
public class ProjectTree {

    public static void make(String pointcut){
        Optional<String> pointcut1 = Optional.of(pointcut);
        AutoCreateAspect aspectAutoCreate = new AutoCreateAspect();
        aspectAutoCreate.handle(pointcut1.orElse(""));
    }

}
