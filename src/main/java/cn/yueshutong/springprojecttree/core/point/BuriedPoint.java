package cn.yueshutong.springprojecttree.core.point;

import cn.yueshutong.springprojecttree.config.ApplicationContextHelper;
import cn.yueshutong.springprojecttree.database.entity.MethodNode;
import cn.yueshutong.springprojecttree.database.service.MethodNodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 埋点处调用的前后方法
 * Create by yster@foxmail.com 2019/1/31 0031 22:56
 */
public class BuriedPoint {
    //自定义线程栈：ThreadId+Stack
    private static final Map<String, Stack<MethodNode>> map = new ConcurrentHashMap<>();
    private static Logger logger = LoggerFactory.getLogger(BuriedPoint.class);
    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    /*
     调用前置方法
     */
    public static void before(String methodId,String className, String methodName, String[] parameterTypes, String[] annotations, String returnType, long threadId, Date startTime, int identify,String superclass,String[] interfaces) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                MethodNode methodNode = new MethodNode(methodId,className, methodName, parameterTypes, annotations, returnType, threadId, startTime, identify,superclass,interfaces);
                pushMap(methodNode);
            }
        });
    }

    /*
    调用后置方法
     */
    public static void after(int identify,long threadId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                popMap(identify,threadId);
            }
        });
    }

    /**
     * push进（线程）栈
     * @param methodNode
     */
    private static void pushMap(MethodNode methodNode) {
        Stack<MethodNode> stack = map.get(String.valueOf(methodNode.getThreadId()));
        if (stack==null){
            stack = new Stack<>();
        }
        stack.push(methodNode);
        map.put(String.valueOf(methodNode.getThreadId()),stack);
    }

    /**
     * pop进（线程）栈
     * @param identify
     * @param threadId
     */
    private static void popMap(int identify, long threadId) {
        Stack<MethodNode> stack = map.get(String.valueOf(threadId));
        if (stack==null||stack.isEmpty()){
            return;
        }
        MethodNode methodNode = stack.pop();
        while (methodNode.getIdentify()!=identify){
            methodNode = stack.pop();
        }
        methodNode.setEndTime(new Date());
        setMembership(stack,methodNode);
    }

    /**
     * 利用栈实现建立节点的父子关系
     * @param stack
     * @param methodNode
     */
    private static void setMembership(Stack<MethodNode> stack, MethodNode methodNode) {
        if (!stack.isEmpty()){
            MethodNode node = stack.pop();
            List<MethodNode> list = node.getMethodNodes();
            if (list==null){
                list = new ArrayList<>();
            }
            list.add(methodNode);
            node.setMethodNodes(list);
            stack.push(node);
        }
        save(methodNode);
    }

    /**
     * 把节点保存到数据库
     * @param methodNode
     */
    private static void save(MethodNode methodNode) {
        try {
            MethodNodeService methodNodeService = ApplicationContextHelper.popBean(MethodNodeService.class);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    methodNodeService.saveNotRedo(methodNode);
                }
            });
        } catch (Exception e) {
            logger.error("请检查是否开启@EnableProjectTree注解?");
            e.printStackTrace();
        }
    }


}
