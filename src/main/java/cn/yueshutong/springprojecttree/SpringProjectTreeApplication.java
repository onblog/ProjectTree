package cn.yueshutong.springprojecttree;

import org.springframework.boot.SpringApplication;

//@SpringBootApplication
//@EnableProjectTree //1
public class SpringProjectTreeApplication {

    public static void main(String[] args) {
//        ProjectTree.make("execution(* cn.yueshutong.springprojecttree..*(..))");//2
        SpringApplication.run(SpringProjectTreeApplication.class, args);
    }

}

