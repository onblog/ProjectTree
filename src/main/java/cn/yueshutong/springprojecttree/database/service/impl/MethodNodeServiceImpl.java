package cn.yueshutong.springprojecttree.database.service.impl;

import cn.yueshutong.springprojecttree.database.dao.MethodNodeDao;
import cn.yueshutong.springprojecttree.database.entity.MethodNode;
import cn.yueshutong.springprojecttree.database.service.MethodNodeService;
import cn.yueshutong.springprojecttree.database.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by yster@foxmail.com 2019/2/1 0001 15:36
 */
@Service
public class MethodNodeServiceImpl implements MethodNodeService {
    @Autowired
    private MethodNodeDao methodNodeDao;

    @Override
    public void saveNotRedo(MethodNode node) {
        //根据方法签名查找是否已存在该方法
        List<MethodNode> methodId = methodNodeDao.findAllByMethodId(node.getMethodId());
        //不存在直接保存,若存在调用链不相同也保存
        if (methodId == null || methodId.size()==0|| !ServiceUtil.analyzeList(node, methodId)) {
            methodNodeDao.save(node);
        }
    }

    @Override
    public List<MethodNode> findAll() {
        return methodNodeDao.findAll();
    }

    @Override
    public MethodNode findAllById(Long id) {
        return methodNodeDao.findAllById(id);
    }

}
