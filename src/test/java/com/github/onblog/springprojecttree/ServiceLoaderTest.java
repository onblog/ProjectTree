package com.github.onblog.springprojecttree;

import org.junit.Test;

import javax.annotation.processing.Processor;
import java.util.ServiceLoader;

/**
 * Created with IntelliJ IDEA.
 * ClassName springprojecttree
 * Create by zhengdong.wang@bloomchic.tech
 * Date 2021/8/7 18:29
 */
public class ServiceLoaderTest {

    @Test
    public void test() {
        ServiceLoader<Processor> loader = ServiceLoader.load(Processor.class);

        for (Processor service : loader) {
            System.out.println(service.getClass());
        }
    }

}
