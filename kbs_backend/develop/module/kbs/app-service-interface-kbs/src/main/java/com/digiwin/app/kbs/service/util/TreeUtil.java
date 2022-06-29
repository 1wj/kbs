package com.digiwin.app.kbs.service.util;

import com.digiwin.app.kbs.service.mws.domain.TreeNodeVO;
import com.digiwin.dmc.sdk.service.impl.UserManagerService;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Date 2022/1/21 10:35
 * @Created yanggld
 * @Description
 */
public class TreeUtil<T extends TreeNodeVO> {


    /**
     * 两层循环实现建树
     *
     * @param treeNodes  传入的树节点列表
     * @param comparator
     * @return
     */
    public static <T extends TreeNodeVO> List<T> bulid(List<T> treeNodes, Object root, Comparator comparator) {
        List<T> trees = new ArrayList<T>();
        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getParentId())) {
                trees.add(treeNode);
            }
            for (T it : treeNodes) {
                if (!StringUtils.isEmpty(it.getParentId()) && it.getParentId().equals(treeNode.getId())) {
                    if (StringUtils.isEmpty(treeNode.getChildren())) {
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.add(it);
                }
            }
            if (comparator != null) {
                treeNode.getChildren().sort(comparator);
            }
        }
        return trees;
    }
}
