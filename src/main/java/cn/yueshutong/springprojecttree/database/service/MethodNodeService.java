package cn.yueshutong.springprojecttree.database.service;

import cn.yueshutong.springprojecttree.database.entity.MethodNode;

import java.util.List;

/**
 * Create by yster@foxmail.com 2019/2/1 0001 15:35
 */
public interface MethodNodeService {

    void saveNotRedo(MethodNode node);

    List<MethodNode> findAll();

    MethodNode findAllById(Long id);
}
