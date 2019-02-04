package cn.yueshutong.springprojecttree.database.dao;

import cn.yueshutong.springprojecttree.database.entity.MethodNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Create by yster@foxmail.com 2019/2/1 0001 15:20
 */
@Repository
public interface MethodNodeDao extends JpaRepository<MethodNode,Long> {

    MethodNode findAllByMethodId(String method);
}
