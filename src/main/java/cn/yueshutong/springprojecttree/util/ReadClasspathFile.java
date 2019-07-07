package cn.yueshutong.springprojecttree.util;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.WeakHashMap;

public class ReadClasspathFile {

    private static WeakHashMap<String, String> map = new WeakHashMap<>();

    public static String read(String classPath) {
        //考虑到数据的一致性，这里没有使用map的containsKey()
        String s = map.get(classPath);
        if (s != null) {
            return s;
        }
        //判空
        ClassPathResource resource = new ClassPathResource(classPath);
        if (!resource.exists()) {
            return null;
        }
        //读取
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //DCL双检查锁
        if (!map.containsKey(classPath)) {
            synchronized (ReadClasspathFile.class) {
                if (!map.containsKey(classPath)) {
                    map.put(classPath, builder.toString());
                }
            }
        }
        return builder.toString();
    }
}
