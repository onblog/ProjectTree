package com.github.onblog.springprojecttree.db.service.impl;

import com.github.onblog.springprojecttree.db.dao.MethodNodeDao;
import com.github.onblog.springprojecttree.db.entity.MethodNode;
import com.github.onblog.springprojecttree.db.service.MethodNodeService;
import com.github.onblog.springprojecttree.db.service.util.ServiceUtil;
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
    public boolean saveNotRedo(MethodNode methodNode) {
        //根据方法签名查找是否已存在该方法
        List<MethodNode> methodList = methodNodeDao.findAllByMethodId(methodNode.getMethodId());
        //不存在直接保存,若存在调用链不相同也保存
        if (methodList == null || methodList.size()==0|| !ServiceUtil.analyzeList(methodNode, methodList)) {
            return methodNodeDao.save(methodNode);
        }
        return false;
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
