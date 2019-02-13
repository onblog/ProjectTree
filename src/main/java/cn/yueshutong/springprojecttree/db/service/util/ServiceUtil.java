package cn.yueshutong.springprojecttree.db.service.util;

import cn.yueshutong.springprojecttree.db.entity.MethodNode;

import java.util.List;

/**
 * Create by yster@foxmail.com 2019/2/3 0003 17:55
 */
public class ServiceUtil {

    /**
     * 分析调用链是否相同
     * 调用链情况相同返回True
     * @param node
     * @param nodeId
     * @return
     */
    public static boolean analyze(MethodNode node, MethodNode nodeId) {
        List<MethodNode> nodes = node.getMethodNodes();
        List<MethodNode> idNodes = nodeId.getMethodNodes();
        int nodeNum = nodes != null ? nodes.size() : 0;
        int idNodeNum = idNodes != null ? idNodes.size() : 0;
        String nodeStr="";
        if (nodeNum>0) {
            StringBuilder builder = new StringBuilder();
            nodes.forEach(s -> builder.append(s.getMethodId()));
            nodeStr = builder.toString();
        }
        String idNodeStr="";
        if (idNodeNum>0) {
            StringBuilder builder = new StringBuilder();
            idNodes.forEach(s -> builder.append(s.getMethodId()));
            idNodeStr = builder.toString();
        }
        return nodeStr.equals(idNodeStr);
    }

    public static boolean analyzeList(MethodNode node, List<MethodNode> nodeId) {
        for (MethodNode methodNode: nodeId){
            if (analyze(node,methodNode)){
                return true;
            }
        }
        return false;
    }

}
