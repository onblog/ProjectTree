package cn.yueshutong.springprojecttree.database.entity;

import cn.yueshutong.springprojecttree.core.common.CodeUtil;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 方法信息
 * Create by yster@foxmail.com 2019/1/31 0031 22:15
 */
@Data
@Entity
@Table(name = "method_node")
public class MethodNode implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String methodId;
    private String className;
    private String methodName;
    private String fullName;
    private String superclass;
    private String[] interfaces;
    private String[] parameterTypes;
    private String[] annotations;
    private String returnType;
    private Long threadId;
    private Date startTime;
    private Date endTime;
    private Long runTime;
    private Integer identify;
    @ElementCollection(fetch = FetchType.EAGER)//定义基本类型或可嵌入类的实例集合
    @OrderColumn(name = "position")//如果使用的是List，你需要多定义一个字段维护集合顺序
    private List<MethodNode> methodNodes;

    public MethodNode() {

    }

    public MethodNode(String methodId,String className, String methodName, String[] parameterTypes, String[] annotations,
                      String returnType, long threadId, Date startTime, int identify,String superclass,String[] interfaces) {
        this.methodId = methodId;
        this.className = className;
        this.methodName = methodName;
        this.parameterTypes = CodeUtil.arrayNotNull(parameterTypes);
        this.annotations = CodeUtil.arrayNotNull(annotations);
        this.returnType = returnType;
        this.startTime = startTime;
        this.threadId = threadId;
        this.identify = identify;
        this.superclass =superclass;
        this.interfaces = CodeUtil.arrayNotNull(interfaces);
    }


    public Long getRunTime() {
        if (this.startTime == null || this.endTime == null) {
            return null;
        }
        return this.endTime.getTime() - this.startTime.getTime();
    }

    public String getFullName() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.className);
        builder.append(".");
        builder.append(this.methodName);
        builder.append("(");
        for (int i=0;i<this.parameterTypes.length;i++){
            if (i>0){
                builder.append(",");
            }
            builder.append(this.parameterTypes[i]);
        }
        builder.append(")");
        return builder.toString();
    }

    public static boolean isIdentify(MethodNode methodNode, int identify) {
        return methodNode.getIdentify() == identify;
    }
}
