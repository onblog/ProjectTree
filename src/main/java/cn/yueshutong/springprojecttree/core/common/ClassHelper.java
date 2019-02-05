package cn.yueshutong.springprojecttree.core.common;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * 从网上随便住找的一个类（自己改了改）
 */
public class ClassHelper {

    /**
     * 从包package中获取所有的Class
     * @param pkg
     * @return
     */
    public static Set<String> getClzFromPkg(String pkg) {
        //去掉*
        pkg = pkg.substring(0,pkg.length()-1);
        //去掉点.
        pkg = removeDot(pkg);
        //第一个class类的集合
        Set<String> classes = new LinkedHashSet<>();
        // 获取包的名字 并进行替换
        String pkgDirName = pkg.replace('.', '/');
        try {
            Enumeration<URL> urls = ClassHelper.class.getClassLoader().getResources(pkgDirName);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                // 如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findClassesByFile(pkg, filePath, classes);
                } else if ("jar".equals(protocol)) {
                    // 如果是jar包文件
                    // 获取jar
                    JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                    //扫描jar包文件 并添加到集合中
                    findClassesByJar(pkg, jar, classes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes.stream().map(s -> s.startsWith(".")?s.substring(1):s).collect(Collectors.toSet());
    }

    private static String removeDot(String name){
        if (name.endsWith(".")){
            return name.substring(0,name.length()-1);
        }
        return name;
    }

    private static void findClassesByFile(String pkgName, String pkgPath, Set<String> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(pkgPath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
//        File[] dirfiles = dir.listFiles(new FileFilter() {
//            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
//            public boolean accept(File file) {
//                return file.isDirectory()|| file.getName().endsWith(".class");
//            }
//        });
        File[] dirfiles = dir.listFiles(pathname -> pathname.isDirectory() || pathname.getName().endsWith("class"));

        if (dirfiles == null || dirfiles.length == 0) {
            return;
        }

        String className;
        Class clz;
        // 循环所有文件
        for (File f : dirfiles) {
            // 如果是目录 则继续扫描
            if (f.isDirectory()) {
                findClassesByFile(pkgName + "." + f.getName(),pkgPath + "/" + f.getName(),classes);
                continue;
            }
            // 如果是java类文件 去掉后面的.class 只留下类名
            className = f.getName();
            className = className.substring(0, className.length() - 6);
            //加载类名
            classes.add(pkgName + "." + className);
//            //加载类
//            clz = loadClass(pkgName + "." + className);
//            // 添加到集合中去
//            if (clz != null) {
//                classes.add(clz);
//            }
        }
    }

    private static void findClassesByJar(String pkgName, JarFile jar, Set<String> classes) {
        String pkgDir = pkgName.replace(".", "/");
        // 从此jar包 得到一个枚举类
        Enumeration<JarEntry> entry = jar.entries();

        JarEntry jarEntry;
        String name, className;
        Class<?> claze;
        // 同样的进行循环迭代
        while (entry.hasMoreElements()) {
            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文
            jarEntry = entry.nextElement();

            name = jarEntry.getName();
            // 如果是以/开头的
            if (name.charAt(0) == '/') {
                // 获取后面的字符串
                name = name.substring(1);
            }

            if (jarEntry.isDirectory() || !name.startsWith(pkgDir) || !name.endsWith(".class")) {
                continue;
            }
            //如果是一个.class文件 而且不是目录
            // 去掉后面的".class" 获取真正的类名
            className = name.substring(0, name.length() - 6);
            //加载类名
            classes.add(className.replace("/", "."));
//            //加载类
//            claze = loadClass(className.replace("/", "."));
//            // 添加到集合中去
//            if (claze != null) {
//                classes.add(claze);
//            }
        }
    }


    private static Class<?> loadClass(String fullClzName) {
        System.out.println(fullClzName);
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(fullClzName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Set<String> clzFromPkg = ClassHelper.getClzFromPkg("");
        for (String clazz: clzFromPkg){
            System.out.println(clazz);
        }

    }

}