package com.github.onblog.springprojecttree.db.service;

import com.github.onblog.springprojecttree.db.entity.MethodNode;

import java.util.List;

/**
 * Create by Martin 2019/2/1 0001 15:35
 */
public interface MethodNodeService {

    boolean saveNotRedo(MethodNode node);

    List<MethodNode> findAll();

    MethodNode findAllById(Long id);
}
