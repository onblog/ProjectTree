package cn.yueshutong.springprojecttree.database.entity;

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
    private boolean first;
    private String methodId;
    private String className;
    private String methodName;
    private String fullName;
    private String superclass;
    private String[] interfaces;
    private String[] parameterTypes;
    private String modifier;
    private String returnType;
    private Long threadId;
    private Date startTime;
    private Date endTime;
    private Long runTime;
    private Integer identify;
    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER) //一对多为Lazy，多对一为Eager
    @JoinColumn(name="cid") //name=定义外键在本表的字段名 rCN=关联外键对象的哪个字段
    private List<MethodNode> methodNodes;

    public MethodNode() {

    }

    public MethodNode(String methodId,String className, String methodName, String[] parameterTypes, String modifier,
                      String returnType, long threadId, Date startTime, int identify,String superclass,String[] interfaces) {
        this.id = -1l;
        this.methodId = methodId;
        this.className = className;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.modifier = modifier;
        this.returnType = returnType;
        this.startTime = startTime;
        this.threadId = threadId;
        this.identify = identify;
        this.superclass =superclass;
        this.interfaces = interfaces;
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
