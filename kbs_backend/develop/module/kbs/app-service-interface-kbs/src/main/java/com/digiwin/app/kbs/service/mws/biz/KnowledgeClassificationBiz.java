package com.digiwin.app.kbs.service.mws.biz;

import com.digiwin.app.kbs.service.mongo.document.KnowledgeClassificationDoc;

import java.util.List;
import java.util.Map;

/**
 * @Date 2022/1/21 15:20
 * @Created yanggld
 * @Description
 */
public interface KnowledgeClassificationBiz {
    /**
     * 创建知识分类
     * @param docList
     */
    void createKnowledgeClassificationInfo(List<KnowledgeClassificationDoc> docList) throws Exception;

    void updateKnowledgeClassificationInfo(List<KnowledgeClassificationDoc> docList) throws Exception;

    void transferKnowledgeClassification(List<Map> modelList);

    void deleteKnowledgeClassificationInfo(List<Map> docList) throws Exception;
}
