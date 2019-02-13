package cn.yueshutong.springprojecttree.core.point;

import cn.yueshutong.springprojecttree.config.ApplicationContextHelper;
import cn.yueshutong.springprojecttree.core.around.util.AroundMethodUtil;
import cn.yueshutong.springprojecttree.db.entity.MethodNode;
import cn.yueshutong.springprojecttree.db.service.MethodNodeService;
import org.aspectj.lang.ProceedingJoinPoint;
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
    public static void before(ProceedingJoinPoint pjp, int identify) {
        long threadId = Thread.currentThread().getId();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                MethodNode methodNode = AroundMethodUtil.getMethodNode(pjp, identify,threadId);
                pushMap(methodNode);
            }
        });
    }

    /*
    调用后置方法
     */
    public static void after(int identify,String returnType) {
        long threadId = Thread.currentThread().getId();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                popMap(identify, threadId, returnType);
            }
        });
    }

    /**
     * push进（线程）栈
     *
     * @param methodNode
     */
    private static void pushMap(MethodNode methodNode) {
        Stack<MethodNode> stack = map.get(String.valueOf(methodNode.getThreadId()));
        if (stack == null) {
            stack = new Stack<>();
        }
        stack.push(methodNode);
        map.put(String.valueOf(methodNode.getThreadId()), stack);
    }

    /**
     * pop出（线程）栈
     *  @param identify
     * @param threadId
     * @param returnType
     */
    private static void popMap(int identify, long threadId, String returnType) {
        Stack<MethodNode> stack = map.get(String.valueOf(threadId));
        if (stack == null || stack.isEmpty()) {
            return;
        }
        MethodNode methodNode = stack.pop();
        while (methodNode.getIdentify() != identify) {
            methodNode = stack.pop();
        }
        methodNode.setEndTime(new Date());
        methodNode.setReturnType(returnType);
        setMembership(stack, methodNode);
    }

    /**
     * 利用栈实现建立节点的父子关系
     *
     * @param stack
     * @param methodNode
     */
    private static void setMembership(Stack<MethodNode> stack, MethodNode methodNode) {
        if (!stack.isEmpty()) {
            MethodNode node = stack.pop();
            List<MethodNode> list = node.getMethodNodes();
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(methodNode);
            node.setMethodNodes(list);
            stack.push(node);
        }else {
            methodNode.setFirst(true);
        }
        save(methodNode);
    }

    /**
     * 把节点保存到数据库
     *
     * @param methodNode
     */
    private static void save(MethodNode methodNode) {
        try {
            MethodNodeService methodNodeService = ApplicationContextHelper.popBean(MethodNodeService.class);
            assert methodNodeService != null;
            methodNodeService.saveNotRedo(methodNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
