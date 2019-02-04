package cn.yueshutong.springprojecttree.core.common;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Create by yster@foxmail.com 2019/2/1 0001 23:07
 */
public class ArraysUtil {
    /**
     * 数组合并
     * @param beans1
     * @param beans2
     * @return
     */
    public static List<String> getListAll(String[] beans1, String[] beans2) {
        List<String> list1 = Arrays.asList(beans1);
        List<String> list2 = Arrays.asList(beans2);
        list1.addAll(list2);
        return list1;
    }

    /**
     * List去重
     * @param list
     */
    public static List<String> removeDuplicate(List<String> list) {
        LinkedHashSet<String> set = new LinkedHashSet<>(list.size());
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }
}
