package com.digiwin.app.kbs.service.mws.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.digiwin.app.kbs.service.mongo.document.KnowledgeBaseDoc;
import com.digiwin.app.kbs.service.mongo.document.KnowledgeClassificationDoc;
import com.digiwin.app.kbs.service.mws.biz.KnowledgeBaseBiz;
import com.digiwin.app.kbs.service.mws.dao.mongo.service.IKnowledgeBaseService;
import com.digiwin.app.kbs.service.mws.domain.model.KnoBaseTreeModel;
import com.digiwin.app.kbs.service.util.TenantTokenUtil;
import com.digiwin.app.kbs.service.util.TreeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @ClassName KnowledgeBaseBizImpl
 * @Description 知识库表 逻辑处理 impl
 * @Version: v1-知识库迭代一
 **/
@Service
public class KnowledgeBaseBizImpl implements KnowledgeBaseBiz {

    @Autowired
    IKnowledgeBaseService knowledgeBaseService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public KnowledgeBaseDoc addKnowledgeClassification(KnowledgeBaseDoc knowledgeBaseDoc) {
        // 逻辑处理
        return knowledgeBaseService.addKnowledgeClassification(knowledgeBaseDoc);
    }

    @Override
    public void createKnowledgeBaseInfo(List<KnowledgeBaseDoc> docList) throws Exception {
        int total = checkSaveKnowledgeBaseInfo(docList).size();
        if (total > 0) {
            throw new Exception("编号已存在");
        }
        mongoTemplate.insertAll(docList);
    }


    @Override
    public void updateKnowledgeBaseInfo(List<KnowledgeBaseDoc> docList) throws Exception {
        List<KnowledgeBaseDoc> dbList = checkSaveKnowledgeBaseInfo(docList);
        for (KnowledgeBaseDoc doc : dbList) {
            ObjectId dbObjId = doc.getObjectId();
            boolean dbObjIdExist = false;
            for (KnowledgeBaseDoc baseDoc : docList) {
                ObjectId objectId = baseDoc.getObjectId();
                if (dbObjId.equals(objectId)) {
                    dbObjIdExist = true;
                }
            }
            // 查出的id不是修改的数据则有已存在的数据
            if (!dbObjIdExist) {
                throw new Exception("编号已存在");
            }
        }
        for (KnowledgeBaseDoc doc : docList) {
            ObjectId objectId = doc.getObjectId();
            KnowledgeBaseDoc dbDoc = mongoTemplate.findById(objectId, KnowledgeBaseDoc.class);
            dbDoc.setBaseNo(doc.getBaseNo());
            dbDoc.setBaseName(doc.getBaseName());
            dbDoc.setBaseDescription(doc.getBaseDescription());
            mongoTemplate.save(dbDoc);
        }
    }

    @Override
    public List<Map<String, Object>> getKnowledgeBaseInfo(String content) throws Exception {
        Pattern pattern = Pattern.compile("^.*" + content + ".*$", Pattern.CASE_INSENSITIVE);
        Criteria criteria = Criteria.where("tenantsid").is(TenantTokenUtil.getTenantSid());
        criteria.orOperator(Criteria.where("base_description").is(pattern), Criteria.where("base_name").is(pattern));
        Query query = new Query(criteria);
        List<KnowledgeBaseDoc> queryList = mongoTemplate.find(query, KnowledgeBaseDoc.class);
        List<Map<String, Object>> resultList = queryList.stream().map(m -> {
            Map<String, Object> map = new HashMap<>();
            map.put("base_id", m.getObjectId().toString());
            map.put("base_no", m.getBaseNo());
            map.put("base_name", m.getBaseName());
            map.put("base_description", m.getBaseDescription());
            map.put("create_date", m.getCreateTime());
            return map;
        }).collect(Collectors.toList());
        return resultList;
    }

    @Override
    public void deleteKnowledgeBaseInfo(List<KnowledgeBaseDoc> docList) throws Exception {
        List<ObjectId> cond = new ArrayList<>();
        for (KnowledgeBaseDoc doc : docList) {
            ObjectId objectId = doc.getObjectId();
            cond.add(objectId);
        }
        Criteria criteria = Criteria.where("_id").in(cond).and("tenantsid").is(TenantTokenUtil.getTenantSid());
        Query query = new Query(criteria);
        List<KnowledgeBaseDoc> queryList = mongoTemplate.find(query, KnowledgeBaseDoc.class);
        for (KnowledgeBaseDoc doc : queryList) {
            List<ObjectId> classificationIds = doc.getClassificationIds();
            List<ObjectId> tagClassificationIds = doc.getTagClassificationIds();
            if (CollectionUtils.isNotEmpty(classificationIds) || CollectionUtils.isNotEmpty(tagClassificationIds)) {
                throw new Exception("知识被引用,无法删除");
            }
        }
        for (KnowledgeBaseDoc doc : docList) {
            mongoTemplate.remove(doc);
        }
    }

    @Override
    public List<KnowledgeBaseDoc> getKnowledgeBaseInfoByName(String name) {
        Criteria criteria = Criteria.where("base_name").is(name).and("tenantsid").is(TenantTokenUtil.getTenantSid());
        Query query = new Query(criteria);
        return mongoTemplate.find(query, KnowledgeBaseDoc.class);
    }

    private List<KnowledgeBaseDoc> checkSaveKnowledgeBaseInfo(List<KnowledgeBaseDoc> docList) {
        List<KnowledgeBaseDoc> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(docList)) {
            return list;
        }
        List<String> cond = new ArrayList<>();
        for (KnowledgeBaseDoc doc : docList) {
            String baseNo = doc.getBaseNo();
            cond.add(baseNo);
        }
        Criteria criteria = Criteria.where("base_no").in(cond).and("tenantsid").is(TenantTokenUtil.getTenantSid());
        Query query = new Query(criteria);
        List<KnowledgeBaseDoc> queryList = mongoTemplate.find(query, KnowledgeBaseDoc.class);
        if (CollectionUtils.isNotEmpty(queryList)) {
            list.addAll(queryList);
        }
        return list;
    }

    @Override
    public void upaddClassificationIds(List<KnowledgeClassificationDoc> list) {
        for (KnowledgeClassificationDoc model : list) {
            String baseDocId = model.getRelegationBaseId();
            ObjectId knoClassificationId = model.getObjectId();
            KnowledgeBaseDoc baseDoc = mongoTemplate.findById(baseDocId, KnowledgeBaseDoc.class);
            List<ObjectId> classificationIds = baseDoc.getClassificationIds();
            if (classificationIds == null) {
                classificationIds = new ArrayList<>();
            }
            for (ObjectId classificationId : classificationIds) {
                if (classificationId.equals(knoClassificationId)) {
                    return;
                }
            }
            classificationIds.add(knoClassificationId);
            baseDoc.setClassificationIds(classificationIds);
            mongoTemplate.save(baseDoc);
        }
    }

    @Override
    public List<Map<String, Object>> getKnowledgeBaseTreeInfo() {
        List<Map<String, Object>> resultList = new ArrayList<>();
        Criteria criteria = Criteria.where("tenantsid").is(TenantTokenUtil.getTenantSid());
        List<KnowledgeBaseDoc> all = mongoTemplate.find(new Query(criteria), KnowledgeBaseDoc.class);
        for (KnowledgeBaseDoc doc : all) {
            ObjectId objectId = doc.getObjectId();
            String baseNo = doc.getBaseNo();
            String baseName = doc.getBaseName();
            Map<String, Object> map = new HashMap<>();
            List<ObjectId> classificationIds = doc.getClassificationIds();
            if (CollectionUtils.isNotEmpty(classificationIds)) {
                Query query = new Query(Criteria.where("objectId").in(classificationIds));
                List<KnowledgeClassificationDoc> classificationDocList = mongoTemplate.find(query, KnowledgeClassificationDoc.class);
                List<KnoBaseTreeModel> treeModelList = classificationDocList.stream().map(
                        m -> {
                            String classificationNo = m.getClassificationNo() == null ? "" : m.getClassificationNo();
                            String parentNo = m.getParentNo() == null ? "" : m.getParentNo();
                            KnoBaseTreeModel knoBaseTreeModel = new KnoBaseTreeModel(classificationNo, parentNo);
                            knoBaseTreeModel.setClassification_id(m.getObjectId().toString());
                            knoBaseTreeModel.setClassification_no(m.getClassificationNo());
                            knoBaseTreeModel.setClassification_name(m.getClassificationName());
                            knoBaseTreeModel.setClassification_description(m.getClassificationDescription());
                            knoBaseTreeModel.setRelegation_base_id(objectId.toString());
                            knoBaseTreeModel.setRelegation_base_name(baseName);
                            return knoBaseTreeModel;
                        }
                ).collect(Collectors.toList());
                List<KnoBaseTreeModel> trees = TreeUtil.bulid(treeModelList, "", null);
                map.put("base_content", trees);
            }
            map.put("base_id", objectId.toString());
            map.put("base_no", baseNo);
            map.put("base_name", baseName);
            map.put("base_description", doc.getBaseDescription());
            resultList.add(map);
        }
        return resultList;
    }



    @Override
    public JSONObject knowledgeDetail(String knowledgeId) throws Exception{
        JSONObject jsonObject = mongoTemplate.findOne(new Query(), JSONObject.class,"kbs_detail");
        return jsonObject;
    }

}
