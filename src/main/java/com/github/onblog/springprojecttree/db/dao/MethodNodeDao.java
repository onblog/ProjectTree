package com.github.onblog.springprojecttree.db.dao;

import com.github.onblog.springprojecttree.db.entity.MethodNode;

import java.util.List;

/**
 * Create by Martin 2019/2/1 0001 15:20
 */
public interface MethodNodeDao {

    MethodNode findAllById(Long id);

    boolean save(MethodNode methodNode);

    List<MethodNode> findAll();

    List<MethodNode> findAllByMethodId(String methonId);
}
