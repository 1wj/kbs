package com.digiwin.app.kbs.service.mws.dao.mongo.service;

import com.digiwin.app.kbs.service.mongo.document.TagClassificationDoc;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * @ClassName ITagClassificationService
 * @Description 标签类别Service
 * @Author HeX
 * @Date 2022/1/19 18:08
 * @Version: v1-知识库迭代一
 **/
public interface ITagClassificationService {
    /**
     * 获取标签类别列表
     * @param tag_classification_name 標籤類別名稱，tag_description 類別描述
     * @return 实体
     */
    List<TagClassificationDoc> getTagClassificationList(String tag_classification_name, String tag_description);
    /**
     * 新增标签分类
     * @param tagClassificationDoc 实体
     * @return 实体
     */
    TagClassificationDoc insertTagClassification(TagClassificationDoc tagClassificationDoc);

    /**
     * 修改标签分类
     * @param tagClassificationDoc 实体
     * @return 实体
     */
    TagClassificationDoc updateTagClassification(TagClassificationDoc tagClassificationDoc);

    /**
     * 删除标签分类
     * @param objectId
     * @return 实体
     */
    Boolean deleteTagClassification(ObjectId objectId);

}
