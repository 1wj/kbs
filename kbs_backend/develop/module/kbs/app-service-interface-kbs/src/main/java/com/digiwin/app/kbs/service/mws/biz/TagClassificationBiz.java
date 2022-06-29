package com.digiwin.app.kbs.service.mws.biz;

import com.digiwin.app.kbs.service.mongo.document.TagClassificationDoc;
import com.digiwin.app.kbs.service.mws.domain.model.TagClassificationModel;
import com.digiwin.app.kbs.service.mws.domain.model.TagClassificationTreeModel;
import com.digiwin.app.kbs.service.mws.domain.model.TagTreeModel;

import java.util.List;
import java.util.Map;

/**
 * @ClassName TagClassificationBiz
 * @Description 标签类别 逻辑处理
 * @Date 2022/1/20 16:06
 * @Version: v1-知识库迭代一
 **/
public interface TagClassificationBiz {
    /**
     * 获取标签类别列表
     * @param content
     * @return 实体
     */
    List<Map<String,Object>> getTagClassificationList(String content) throws Exception;
    /**
     * 新增标签类别
     * @param tagClassificationModel 实体
     * @return 实体
     */
    TagClassificationDoc insertTagClassification(TagClassificationModel tagClassificationModel) throws Exception;

    /**
     * 修改标签类别
     * @param tagClassificationModel 实体
     * @return 实体
     */
    void updateTagClassification(TagClassificationModel tagClassificationModel) throws Exception;

    /**
     * 删除标签类别
     * @param modelList
     * @return 实体
     */
    void deleteTagClassification(List<TagClassificationModel> modelList) throws Exception;


    /**
     * 转移标签类别
     * @param tagClassificationModel
     * @return 实体
     */
    void transferTagClassification(TagClassificationModel tagClassificationModel) throws Exception;

    /**
     * 标签分类树
     * @return
     */
    List<TagClassificationTreeModel> getTagClassificationTree(String baseId);

    /**
     * 标签树
     * @return
     */
    List<TagTreeModel> getTagTree(String baseId);
}
