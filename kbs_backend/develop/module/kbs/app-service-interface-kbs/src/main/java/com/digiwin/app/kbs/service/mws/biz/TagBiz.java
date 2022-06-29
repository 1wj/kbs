package com.digiwin.app.kbs.service.mws.biz;

import com.alibaba.fastjson.JSONObject;
import com.digiwin.app.kbs.service.mongo.document.TagDoc;
import com.digiwin.app.kbs.service.mws.domain.model.TagModel;

import java.util.List;
import java.util.Map;

/**
 * @ClassName TagClassificationBiz
 * @Description 标签 逻辑处理
 * @Date 2022/1/20 16:06
 * @Version: v1-知识库迭代一
 **/
public interface TagBiz {
    /**
     * 获取标签列表
     * @param param
     * @return 实体
     */
    List<Map<String, Object>> getTagList(JSONObject param);
    /**
     * 新增标签
     * @param tagModel 实体
     * @return 实体
     */
    TagDoc insertTag(TagModel tagModel) throws Exception;

    /**
     * 修改标签
     * @param tagModel 实体
     * @return 实体
     */
    void updateTag(TagModel tagModel) throws Exception;

    /**
     * 标签生效/失效
     * @param docList 实体
     * @return 实体
     */
    void updateTagStatus(List<TagDoc> docList) throws Exception;

    /**
     * 删除标签
     * @param docList
     * @return 实体
     */
    void deleteTag(List<TagDoc> docList) throws Exception;

    /**
     * 標籤複製轉移
     * @param tagModel
     * @return 实体
     */
    void copyTransferTag(TagModel tagModel) throws Exception;

}
