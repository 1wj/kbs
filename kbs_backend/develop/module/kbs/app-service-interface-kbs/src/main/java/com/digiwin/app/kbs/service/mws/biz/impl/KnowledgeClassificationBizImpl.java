package com.digiwin.app.kbs.service.mws.biz.impl;

import com.digiwin.app.kbs.service.mongo.document.KnowledgeBaseDoc;
import com.digiwin.app.kbs.service.mongo.document.KnowledgeClassificationDoc;
import com.digiwin.app.kbs.service.mws.biz.KnowledgeBaseBiz;
import com.digiwin.app.kbs.service.mws.biz.KnowledgeClassificationBiz;
import com.digiwin.app.kbs.service.util.TenantTokenUtil;
import org.apache.commons.collections.CollectionUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Date 2022/1/21 15:20
 * @Created yanggld
 * @Description
 */
@Service
public class KnowledgeClassificationBizImpl implements KnowledgeClassificationBiz {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private KnowledgeBaseBiz knowledgeBaseBiz;

    @Override
    public void createKnowledgeClassificationInfo(List<KnowledgeClassificationDoc> docList) throws Exception {
        int total = checkSaveKnowledgeClassificationInfo(docList).size();
        if (total > 0) {
            throw new Exception("编号已存在");
        }
        mongoTemplate.insertAll(docList);
        // 更新知识库的分类字段
        knowledgeBaseBiz.upaddClassificationIds(docList);
    }

    @Override
    public void updateKnowledgeClassificationInfo(List<KnowledgeClassificationDoc> docList) throws Exception {
        List<KnowledgeClassificationDoc> dbList = checkSaveKnowledgeClassificationInfo(docList);
        for (KnowledgeClassificationDoc doc : dbList) {
            ObjectId dbObjId = doc.getObjectId();
            boolean dbObjIdExist = false;
            for (KnowledgeClassificationDoc  classificationDoc: docList) {
                ObjectId objectId = classificationDoc.getObjectId();
                if (dbObjId.equals(objectId)) {
                    dbObjIdExist = true;
                }
            }
            // 查出的id不是修改的数据则有已存在的数据
            if (!dbObjIdExist) {
                throw new Exception("编号已存在");
            }
        }
        for (KnowledgeClassificationDoc doc : docList) {
            ObjectId objectId = doc.getObjectId();
            KnowledgeClassificationDoc dbDoc = mongoTemplate.findById(objectId, KnowledgeClassificationDoc.class);
            dbDoc.setClassificationNo(doc.getClassificationNo());
            dbDoc.setClassificationName(doc.getClassificationName());
            dbDoc.setRelegationBaseName(doc.getRelegationBaseName());
            dbDoc.setParentNo(doc.getParentNo());
            dbDoc.setClassificationDescription(doc.getClassificationDescription());
            mongoTemplate.save(dbDoc);
        }
    }

    @Override
    public void transferKnowledgeClassification(List<Map> modelList) {
        for (Map<String,String> model : modelList) {
            String classificationId = model.get("classification_id");
            String parentNo = model.get("parent_no");
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(classificationId));
            Update update = new Update();
            update.set("parent_no", parentNo);
            mongoTemplate.updateFirst(query, update, KnowledgeClassificationDoc.class);
        }
    }

    @Override
    public void deleteKnowledgeClassificationInfo(List<Map> list) throws Exception {
        List<String> idList = new ArrayList<>();
        for (Map<String,String> map : list) {
            String id = map.get("classification_id");
            idList.add(id);
        }
        Criteria criteria = Criteria.where("_id").in(idList).and("tenantsid").is(TenantTokenUtil.getTenantSid());
        Query query = new Query(criteria);
        List<KnowledgeClassificationDoc> docList = mongoTemplate.find(query, KnowledgeClassificationDoc.class);
        checkDeleteKnowledgeClassificationInfo(docList);
        for (KnowledgeClassificationDoc doc : docList) {
            ObjectId knowledgeClassificationObjectId = doc.getObjectId();
            String relegationBaseId = doc.getRelegationBaseId();
            mongoTemplate.remove(doc);
            // 从知识库分类字段中剔除
            KnowledgeBaseDoc baseDoc = mongoTemplate.findById(relegationBaseId, KnowledgeBaseDoc.class);
            List<ObjectId> classificationIds = baseDoc.getClassificationIds();
            if (classificationIds.contains(knowledgeClassificationObjectId)) {
                classificationIds.remove(knowledgeClassificationObjectId);
                baseDoc.setClassificationIds(classificationIds);
                mongoTemplate.save(baseDoc);
            }
        }
    }

    /**
     * 删除类别校验
     * @param docList
     */
    private void checkDeleteKnowledgeClassificationInfo(List<KnowledgeClassificationDoc> docList) throws Exception {
        List<String> noList = new ArrayList<>();
        for (KnowledgeClassificationDoc doc : docList) {
            String classificationNo = doc.getClassificationNo();
            noList.add(classificationNo);
        }
        Criteria criteria = Criteria.where("parent_no").in(noList).and("tenantsid").is(TenantTokenUtil.getTenantSid());
        Query query = new Query(criteria);
        List<KnowledgeClassificationDoc> queryList = mongoTemplate.find(query, KnowledgeClassificationDoc.class);
        // 若该类别下有子类别，不可删
        if (CollectionUtils.isNotEmpty(queryList)) {
            throw new Exception("有子分类不能删除");
        }
        //TODO 若该类别被知识引用，不可删
    }


    private List<KnowledgeClassificationDoc> checkSaveKnowledgeClassificationInfo(List<KnowledgeClassificationDoc> docList) {
        List<KnowledgeClassificationDoc> list = new ArrayList<>();
        if(CollectionUtils.isEmpty(docList)) {
            return list;
        }
        List<String> cond = new ArrayList<>();
        for (KnowledgeClassificationDoc doc : docList) {
            String no = doc.getClassificationNo();
            cond.add(no);
        }
        Criteria criteria = Criteria.where("classification_no").in(cond).and("tenantsid").is(TenantTokenUtil.getTenantSid());
        Query query = new Query(criteria);
        List<KnowledgeClassificationDoc> queryList = mongoTemplate.find(query, KnowledgeClassificationDoc.class);
        if (CollectionUtils.isNotEmpty(queryList)) {
            list.addAll(queryList);
        }
        return list;
    }
}
