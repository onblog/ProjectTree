package cn.yueshutong.springprojecttree.core.aspect;

import cn.yueshutong.springprojecttree.core.aspect.util.JarTool;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.jar.JarFile;

/**
 * Create by yster@foxmail.com 2019/2/7 0007 12:40
 */
@Deprecated
public class AutoCreateAspect {
    private final static String resource = "spring/spring-aop.xml";

    public void handle(String pointcut) {
        try {
            String javaCode = getXmlCode(pointcut);
            if (javaCode == null) {
                return;
            }
            //获取当前应用的classpath路径
            URL url = Thread.currentThread().getContextClassLoader().getResource("");
            String protocol = url.getProtocol(); //路径协议
            String path = url.getPath(); //路径字符串形式
            if ("file".equals(protocol)) {
                File javaFile = new File(path +File.separator+resource);
                FileUtils.writeStringToFile(javaFile, javaCode, "UTF-8");
            }else if ("jar".equals(protocol)) {
                String file = System.getProperty("java.class.path"); //当前jar包位置/xxx.jar
                String prefix = path.substring(path.indexOf(".jar!")+6).replaceAll("!","");//内classpath路径
                JarTool.newInstance().write(new JarFile(new File(file)), prefix+resource, javaCode);
            }
        } catch (IOException e) {

        }
    }


    /**
     * 读取resource文件内容
     *
     * @param pointcut
     * @return
     * @throws IOException
     */
    public static String getXmlCode(String pointcut) throws IOException {
        InputStream stream = AutoCreateAspect.class.getClassLoader().getResourceAsStream("spring/spring-aop-demo.xml");
        String javaCode = IOUtils.toString(stream, "UTF-8");
        if (javaCode.contains("expression=\"" + pointcut + "\"")) {
            return null;
        }
        javaCode = javaCode.replaceAll("expression=\".*\"", "expression=\"" + pointcut + "\"");
        stream.close();
        return javaCode;
    }



//    public void handle(String pointcut) {
//        try {
//            String xmlCode = getXmlCode(pointcut);
//            if (xmlCode == null) {
//                return;
//            }
//            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources("");
//            while (urls.hasMoreElements()) {
//                URL url = urls.nextElement();
//                String protocol = url.getProtocol();//得到协议的名称
//                if ("file".equals(protocol)) {
//                    // 获取包的物理路径
//                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
//                    File javaFile = new File(filePath + File.separator + resource);
//                    FileUtils.writeStringToFile(javaFile, xmlCode, "UTF-8");
//                } else if ("jar".equals(protocol)) {
//                    // 如果是jar包文件,获取jar
////                    JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
//                    String file = System.getProperty("java.class.path");
//                    System.out.println("获取文件：" + file);
//                    JarTool.newInstance().write(new JarFile(new File(file)), resource, xmlCode);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
