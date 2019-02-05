package cn.yueshutong.springprojecttree.core.common;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 输入import形式的字符串
 * 返回class类的全限定名
 * Create by yster@foxmail.com 2019/2/2 0002 20:43
 */
public class PackageUtil {
    private static final String base = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static Logger logger = LoggerFactory.getLogger(PackageUtil.class);

    /**
     * 传入cn.yueshutong.*<br>cn.yueshutong.App</br>
     * 返回包下的所有类的全限定名集合
     * @param path
     * @return
     */
    public static Set<String> scanClassNameToSet(String[] path){
        Set<String> set = new HashSet<>();
        for (int i = 0; i < path.length; i++) {
            set.addAll(Arrays.stream(scanOneClass(path[i])).collect(Collectors.toSet()));
        }
        return set;
    }


    /**
     * 如果是包、是类
     * @param s
     * @return
     */
    private static String[] scanOneClass(String s) {
        //如果是包
        if (s.endsWith("*")){
            return ClassHelper.getClzFromPkg(s).toArray(new String[0]);
        }
        //如果是类
        return new String[]{s};
    }

    /**
     * 无法获取jar包内的class文件，所以废弃
     * @param s
     * @return
     */
    @Deprecated
    private static String[] scanManyClasss(String s) {
        //去掉*,作为包名前缀
        final String prefix = s.substring(0, s.length() - 1);
        String path = prefix;
        if (prefix.contains(".")){
            path = prefix.replaceAll("\\.", File.separator);
        }
        // 获取此包的目录建立一个File
        File dir = new File(base + File.separator + path);

        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            logger.warn("用户定义包名 " + dir.getPath() + " 下没有任何文件");
            return null;
        }

        // 如果存在 就获取包下的所有文件 包括目录
        Collection<File> files = FileUtils.listFiles(dir, new IOFileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".class");
            }

            @Override
            public boolean accept(File dir, String name) {
                return false;
            }
        }, TrueFileFilter.INSTANCE);

        Set<String> collect = files.stream().map(file -> {
            String replace = file.getPath().replace(dir.getPath(), "").replace(File.separator, ".").replace(".class", "");
            if (replace.startsWith(".")) {
                replace = replace.substring(1);
            }
//            logger.debug(prefix + replace);
            return prefix + replace;
        }).collect(Collectors.toSet());

        return collect.toArray(new String[0]);
    }
}
