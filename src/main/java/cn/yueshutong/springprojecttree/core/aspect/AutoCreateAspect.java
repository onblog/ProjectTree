package cn.yueshutong.springprojecttree.core.aspect;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Create by yster@foxmail.com 2019/2/7 0007 12:40
 */
public class AutoCreateAspect {
    private final String resource = "spring/spring-aop";
    private final String xmlFile = resource + "-pt.xml";
    private final String classapth = AutoCreateAspect.class.getClassLoader().getResource("").getPath();

    public void handle(String pointcut) {
        try {
            InputStream stream = AutoCreateAspect.class.getClassLoader().getResourceAsStream(resource);
            File javaFile = new File(classapth+File.separator+xmlFile);
            FileUtils.copyInputStreamToFile(stream,javaFile);
            String javaCode = FileUtils.readFileToString(javaFile, "UTF-8");
            javaCode = javaCode.replaceAll("expression=\".*\"", "expression=\""+pointcut+"\"");
            FileUtils.writeStringToFile(javaFile,javaCode,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
