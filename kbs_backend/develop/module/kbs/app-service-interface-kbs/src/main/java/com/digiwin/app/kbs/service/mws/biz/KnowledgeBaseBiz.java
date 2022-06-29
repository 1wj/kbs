package com.digiwin.app.kbs.service.mws.biz;

import com.alibaba.fastjson.JSONObject;
import com.digiwin.app.kbs.service.mongo.document.KnowledgeBaseDoc;
import com.digiwin.app.kbs.service.mongo.document.KnowledgeClassificationDoc;

import java.util.List;
import java.util.Map;

/**
 * @ClassName KnowledgeBaseBiz
 * @Description 知识库表 逻辑处理
 * @Date 2022/1/19 16:06
 * @Version: v1-知识库迭代一
 **/
public interface KnowledgeBaseBiz {
    /**
     * 新增知识库
     * @param knowledgeBaseDoc 实体
     * @return 实体
     */
    KnowledgeBaseDoc addKnowledgeClassification(KnowledgeBaseDoc knowledgeBaseDoc);

    /**
     * 创建知识库
     * @param docList
     */
    void createKnowledgeBaseInfo(List<KnowledgeBaseDoc> docList) throws Exception;

    /**
     * 更新知识库
     * @param docList
     */
    void updateKnowledgeBaseInfo(List<KnowledgeBaseDoc> docList) throws Exception;

    /**
     * 知识tree
     * @return
     */
    List<Map<String, Object>> getKnowledgeBaseTreeInfo();

    /**
     * 更新知识库类别
     * @param list
     */
    void upaddClassificationIds(List<KnowledgeClassificationDoc> list);

    /**
     * 知识库查询
     * @param content
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> getKnowledgeBaseInfo(String content) throws Exception;

    /**
     * 删除
     * @param docList
     */
    void deleteKnowledgeBaseInfo(List<KnowledgeBaseDoc> docList) throws Exception;

    /**
     * 获取知识详情
     * @param knowledgeId
     * @return
     * @throws Exception
     */
    JSONObject knowledgeDetail(String knowledgeId) throws Exception;


    List<KnowledgeBaseDoc> getKnowledgeBaseInfoByName(String name);
}
