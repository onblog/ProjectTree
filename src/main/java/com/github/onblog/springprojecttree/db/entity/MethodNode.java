package com.github.onblog.springprojecttree.db.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 方法信息
 * Create by Martin 2019/1/31 0031 22:15
 */
public class MethodNode implements Serializable {
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
    private List<MethodNode> methodNodes;

    public MethodNode() {

    }

    public MethodNode(String methodId,String className, String methodName, String[] parameterTypes, String modifier,
                      String returnType, long threadId, Date startTime, int identify,String superclass,String[] interfaces) {
        this.id = -1L;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSuperclass() {
        return superclass;
    }

    public void setSuperclass(String superclass) {
        this.superclass = superclass;
    }

    public String[] getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(String[] interfaces) {
        this.interfaces = interfaces;
    }

    public String[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(String[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public Long getThreadId() {
        return threadId;
    }

    public void setThreadId(Long threadId) {
        this.threadId = threadId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setRunTime(Long runTime) {
        this.runTime = runTime;
    }

    public Integer getIdentify() {
        return identify;
    }

    public void setIdentify(Integer identify) {
        this.identify = identify;
    }

    public List<MethodNode> getMethodNodes() {
        return methodNodes;
    }

    public void setMethodNodes(List<MethodNode> methodNodes) {
        this.methodNodes = methodNodes;
    }
}
