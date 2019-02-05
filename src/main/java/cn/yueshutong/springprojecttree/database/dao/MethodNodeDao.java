package cn.yueshutong.springprojecttree.database.dao;

import cn.yueshutong.springprojecttree.database.entity.MethodNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Create by yster@foxmail.com 2019/2/1 0001 15:20
 */
@Repository
public interface MethodNodeDao extends JpaRepository<MethodNode,Long> {

    MethodNode findAllById(Long id);

    List<MethodNode> findAllByMethodId(String methonId);
}
