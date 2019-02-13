package cn.yueshutong.springprojecttree;


import cn.yueshutong.springprojecttree.config.annotation.EnableProjectTree;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
//@EnableProjectTree("execution(* cn.yueshutong.springprojecttree..*(..))")
public class SpringProjectTreeApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringProjectTreeApplication.class, args);
    }
}

