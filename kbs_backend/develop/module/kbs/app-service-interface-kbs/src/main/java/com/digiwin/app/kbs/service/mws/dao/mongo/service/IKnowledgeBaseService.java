package com.digiwin.app.kbs.service.mws.dao.mongo.service;

import com.alibaba.fastjson.JSONObject;
import com.digiwin.app.kbs.service.mongo.document.KnowledgeBaseDoc;

import java.util.List;

/**
 * @ClassName IKnowledgeBaseService
 * @Description 知识库Service
 * @Author HeX
 * @Date 2022/1/19 18:08
 * @Version: v1-知识库迭代一
 **/
public interface IKnowledgeBaseService {
    /**
     * 新增知识库
     * @param knowledgeBaseDoc 实体
     * @return 实体
     */
    KnowledgeBaseDoc addKnowledgeClassification(KnowledgeBaseDoc knowledgeBaseDoc);




}
