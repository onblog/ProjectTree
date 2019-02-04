package cn.yueshutong.springprojecttree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableProjectTree //第一步
public class SpringProjectTreeApplication {

    public static void main(String[] args) {
//        ProjectTree.make(new String[]{"cn.yueshutong.springprojecttree.demo.*"},new String[]{});//第二步
        SpringApplication.run(SpringProjectTreeApplication.class, args);
    }

}

