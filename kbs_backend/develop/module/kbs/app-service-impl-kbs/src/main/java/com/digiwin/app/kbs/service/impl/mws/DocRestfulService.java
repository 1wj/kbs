package com.digiwin.app.kbs.service.impl.mws;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.digiwin.app.kbs.service.mongo.document.KnowledgeBaseDoc;
import com.digiwin.app.kbs.service.mongo.document.KnowledgeClassificationDoc;
import com.digiwin.app.kbs.service.util.BizUtil;
import com.digiwin.app.kbs.service.mws.biz.KnowledgeBaseBiz;
import com.digiwin.app.kbs.service.mws.biz.KnowledgeClassificationBiz;
import com.digiwin.app.kbs.service.mws.domain.model.KnoBaseModel;
import com.digiwin.app.kbs.service.mws.domain.model.KnoClassificationModel;
import com.digiwin.app.kbs.service.mws.restful.IDocRestFulService;
import com.digiwin.app.service.DWServiceResult;
import org.apache.commons.collections.CollectionUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Date 2022/1/20 11:26
 * @Created yanggld
 * @Description
 */
public class DocRestfulService implements IDocRestFulService {
    Logger logger = LoggerFactory.getLogger(DocRestfulService.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private KnowledgeBaseBiz knowledgeBaseBiz;

    @Autowired
    private KnowledgeClassificationBiz knowledgeClassificationBiz;

    @Override
    public Object createKnowledgeBaseInfo(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            List<KnoBaseModel> modelList = BizUtil.getKnowledgeReqBizParam(messageBody, "knowledge_base_info", KnoBaseModel.class);
            List<KnowledgeBaseDoc> docList = BizUtil.convertList(modelList, KnowledgeBaseDoc.class);
            knowledgeBaseBiz.createKnowledgeBaseInfo(docList);
            Map<String, Object> map = new HashMap<>();
            if (CollectionUtils.isNotEmpty(docList)) {
                KnowledgeBaseDoc knowledgeBaseDoc = docList.get(0);
                Map<String, Object> map1 = new HashMap<>();
                map1.put("base_id", knowledgeBaseDoc.getObjectId().toString());
                map.put("knowledge_base_info", map1);
            }
            result.setData(map);
            result.setSuccess(true);
            result.setMessage("success!");
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @Override
    public Object updateKnowledgeBaseInfo(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            List<KnoBaseModel> modelList = BizUtil.getKnowledgeReqBizParam(messageBody, "knowledge_base_info", KnoBaseModel.class);
            List<KnowledgeBaseDoc> docList = BizUtil.convertListWithFunc(modelList, KnowledgeBaseDoc.class, (obj, knowledgeBaseDoc) -> {
                KnoBaseModel m = (KnoBaseModel) obj;
                knowledgeBaseDoc.setObjectId(new ObjectId(m.getObjectId()));
            });
            knowledgeBaseBiz.updateKnowledgeBaseInfo(docList);
            result.setSuccess(true);
            result.setMessage("success!");
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @Override
    public Object deleteKnowledgeBaseInfo(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            List<Map> mapList = BizUtil.getKnowledgeReqBizParam(messageBody, "knowledge_base_info", Map.class);
            List<KnowledgeBaseDoc> docList = mapList.stream().map(map -> {
                Object baseIdObj = map.get("base_id");
                if (ObjectUtils.isEmpty(baseIdObj)) {
                    return null;
                }
                KnowledgeBaseDoc doc = new KnowledgeBaseDoc();
                doc.setObjectId(new ObjectId(baseIdObj.toString()));
                return doc;
            }).filter(Objects::nonNull).collect(Collectors.toList());
            knowledgeBaseBiz.deleteKnowledgeBaseInfo(docList);
            result.setSuccess(true);
            result.setMessage("success!");
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @Override
    public Object verifyKnowledgeBaseName(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            List<Map> modelList = BizUtil.getKnowledgeReqBizParam(messageBody, "base_info", Map.class);
            Map<String, String> dataMap = modelList.get(0);
            String name = dataMap.get("base_name");
            List<KnowledgeBaseDoc> list = knowledgeBaseBiz.getKnowledgeBaseInfoByName(name);
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> map1 = new HashMap<>();
            map1.put("repeat",!CollectionUtils.isEmpty(list));
            map.put("verify_info", map1);
            result.setData(map);
            result.setSuccess(true);
            result.setMessage("success!");
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @Override
    public Object getKnowledgeBaseInfo(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            List<Map> modelList = BizUtil.getKnowledgeReqBizParam(messageBody, "knowledge_base_info", Map.class);
            Map<String, String> dataMap = modelList.get(0);
            String content = dataMap.get("search_content");
            List<Map<String, Object>> resultList = knowledgeBaseBiz.getKnowledgeBaseInfo(content);
            Map<String, Object> map = new HashMap<>();
            map.put("knowledge_base_info", resultList);
            result.setData(map);
            result.setSuccess(true);
            result.setMessage("success!");
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @Override
    public Object getKnowledgeBaseTreeInfo(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            List<Map<String, Object>> resultList = knowledgeBaseBiz.getKnowledgeBaseTreeInfo();
            Map<String, Object> map = new HashMap<>();
            map.put("knowledge_base_info", resultList);
            result.setData(map);
            result.setSuccess(true);
            result.setMessage("success!");
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @Override
    public Object createKnowledgeClassificationInfo(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            List<KnoClassificationModel> modelList = BizUtil.getKnowledgeReqBizParam(messageBody, "knowledge_classification_info", KnoClassificationModel.class);
            List<KnowledgeClassificationDoc> docList = BizUtil.convertList(modelList, KnowledgeClassificationDoc.class);
            knowledgeClassificationBiz.createKnowledgeClassificationInfo(docList);
            Map<String, Object> map = new HashMap<>();
            if (CollectionUtils.isNotEmpty(docList)) {
                KnowledgeClassificationDoc doc = docList.get(0);
                Map<String, Object> map1 = new HashMap<>();
                map1.put("classification_id", doc.getObjectId().toString());
                map.put("knowledge_classification_info", map1);
            }
            result.setData(map);
            result.setSuccess(true);
            result.setMessage("success!");
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @Override
    public Object updateKnowledgeClassificationInfo(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            List<KnoClassificationModel> modelList = BizUtil.getKnowledgeReqBizParam(messageBody, "knowledge_classification_info", KnoClassificationModel.class);
            List<KnowledgeClassificationDoc> docList = BizUtil.convertListWithFunc(modelList, KnowledgeClassificationDoc.class, (obj, doc) -> {
                KnoClassificationModel m = (KnoClassificationModel) obj;
                doc.setObjectId(new ObjectId(m.getObjectId()));
            });
            knowledgeClassificationBiz.updateKnowledgeClassificationInfo(docList);
            result.setSuccess(true);
            result.setMessage("success!");
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @Override
    public Object deleteKnowledgeClassificationInfo(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            List<Map> mapList = BizUtil.getKnowledgeReqBizParam(messageBody, "knowledge_classification_info", Map.class);
            knowledgeClassificationBiz.deleteKnowledgeClassificationInfo(mapList);
            result.setSuccess(true);
            result.setMessage("success!");
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @Override
    public Object transferKnowledgeClassification(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            List<Map> modelList = BizUtil.getKnowledgeReqBizParam(messageBody, "knowledge_classification_info", Map.class);
            knowledgeClassificationBiz.transferKnowledgeClassification(modelList);
            result.setSuccess(true);
            result.setMessage("success!");
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            return result;
        }
    }








}
