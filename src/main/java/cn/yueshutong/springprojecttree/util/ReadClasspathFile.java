package cn.yueshutong.springprojecttree.util;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.WeakHashMap;

public class ReadClasspathFile {

    private static WeakHashMap<String,String> map = new WeakHashMap<>();

    public static String read(String classPath) throws IOException {
        //考虑到数据的一致性，这里没有使用map的containsKey()
        String s = map.get(classPath);
        if (s!=null){
            return s;
        }
        ClassPathResource resource = new ClassPathResource(classPath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(),"UTF-8"));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine())!=null){
            builder.append(line+"\n");
        }
        //DCL双检查锁
        if (!map.containsKey(classPath)){
            synchronized (ReadClasspathFile.class){
                if (!map.containsKey(classPath)){
                    map.put(classPath,builder.toString());
                }
            }
        }
        return builder.toString();
    }
}
