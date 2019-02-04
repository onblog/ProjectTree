package cn.yueshutong.springprojecttree.config.annotation;

import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@ComponentScan //1
@ComponentScan(basePackages = "cn.yueshutong.springprojecttree") //2
public @interface EnableProjectTree {
    String[] value() default{};
}
