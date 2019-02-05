package cn.yueshutong.springprojecttree.core;


import cn.yueshutong.springprojecttree.core.common.CodeUtil;
import cn.yueshutong.springprojecttree.core.common.PackageUtil;
import cn.yueshutong.springprojecttree.core.point.BuriedPoint;
import cn.yueshutong.springprojecttree.core.point.ByteCode;
import javassist.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;

/**
 * 对class字节码进行处理
 * Create by yster@foxmail.com 2019/1/31 0031 22:10
 */
class ByteCodeAdvice {
    private Logger logger = LoggerFactory.getLogger(ByteCodeAdvice.class);

    /**
     * 埋点入口
     * @param basePackage 要埋点的类
     * @param exclude 要排除的类
     */
    void advice(String[] basePackage,String[] exclude) {
        Set<String> baseClass = PackageUtil.scanClassNameToSet(basePackage);
        Set<String> excludeClass = PackageUtil.scanClassNameToSet(exclude);
        for (String name : baseClass) {
            if (!excludeClass.contains(name)) {
                logger.debug(name);
                handle(name);
            }
        }
    }

    /**
     * 开始对class进行处理
     * @param clazz
     * @return
     */
    private Object handle(String clazz) {
        try {
            ClassPool pool = ClassPool.getDefault();
            pool.insertClassPath(new ClassClassPath(ByteCodeAdvice.class));
            CtClass cc = pool.get(clazz);
            //校验是否可被实例化
            if (!CodeUtil.checkout(cc)) {
                return null;
            }
            //方法字节码增强
             methodAdvice(cc, pool);
            //写入磁盘
            //cc.writeFile();
            //务必立刻实例化对象
            Class c = cc.toClass();
            //解冻
            cc.defrost();
            return c.newInstance();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 方法字节码增强处理
     * @param cc
     * @param classPool
     * @throws CannotCompileException
     * @throws NotFoundException
     * @throws IOException
     */
    private void methodAdvice(CtClass cc, ClassPool classPool) throws CannotCompileException, NotFoundException, IOException {
        //导入Import
        classPool.importPackage(BuriedPoint.class.getName());
        classPool.importPackage(CodeUtil.class.getName());
        //获取本类实现的方法
        CtMethod[] methods = cc.getDeclaredMethods();
        for (CtMethod method : methods) {
            //获取一系列属性
            String methodId = CodeUtil.quotationMark(CodeUtil.genericSignature(cc,method));
            String className = CodeUtil.quotationMark(cc.getName());
            String methodName = CodeUtil.quotationMark(method.getName());
            String parameterTypes = CodeUtil.arrayMark(Arrays.stream(method.getParameterTypes()).map(CtClass::getName).toArray(String[]::new));
            String annotations = CodeUtil.arrayMark(Arrays.stream(method.getAvailableAnnotations()).map(s -> s.getClass().getName()).toArray(String[]::new));
            String returnType = CodeUtil.quotationMark(method.getReturnType().getName());
            int identify = new Random().nextInt();
            String superclass = CodeUtil.quotationMark(cc.getSuperclass().getName());
            String interfaces = CodeUtil.arrayMark(Arrays.stream(cc.getInterfaces()).map(CtClass::getName).toArray(String[]::new));
            //插入之前代码
            StringBuilder before = ByteCode.getBeforeCode(methodId,className, methodName, parameterTypes, annotations, returnType, identify, superclass, interfaces);
            method.insertBefore(before.toString());
            //插入之后代码
            StringBuilder after = ByteCode.getCodeAfter(identify);
            method.insertAfter(after.toString());
        }
    }


}
