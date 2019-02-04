package cn.yueshutong.springprojecttree.core.point;

import cn.yueshutong.springprojecttree.core.common.CodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 埋点代码（字符串形式）
 * Create by yster@foxmail.com 2019/2/3 0003 14:53
 */
public class ByteCode {
    private static Logger logger = LoggerFactory.getLogger(ByteCode.class);

    public static StringBuilder getBeforeCode(String methodId, String className, String methodName, String parameterTypes, String annotations, String returnType, int identify, String superclass, String interfaces) {
        StringBuilder before = new StringBuilder();
        before.append("{");
        before.append("BuriedPoint.before(");
        before.append(methodId);
        before.append("," + className);
        before.append("," + methodName);
        before.append("," + parameterTypes);
        before.append("," + annotations);
        before.append("," + returnType);
        before.append("," + CodeUtil.threadId());
        before.append("," + CodeUtil.date());
        before.append("," + identify);
        before.append("," + superclass);
        before.append("," + interfaces);
        before.append(");");
        before.append("}");
//        logger.debug(before.toString());
        return before;
    }

    public static StringBuilder getCodeAfter(int identify) {
        StringBuilder after = new StringBuilder();
        after.append("{");
        after.append("BuriedPoint.after(");
        after.append(identify);
        after.append("," + CodeUtil.threadId());
        after.append(");");
        after.append("}");
//        logger.debug(after.toString());
        return after;
    }

}
