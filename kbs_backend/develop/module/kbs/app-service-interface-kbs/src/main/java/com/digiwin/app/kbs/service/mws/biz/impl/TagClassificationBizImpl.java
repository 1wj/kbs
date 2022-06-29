package com.digiwin.app.kbs.service.mws.biz.impl;

import com.digiwin.app.kbs.service.mongo.document.KnowledgeBaseDoc;
import com.digiwin.app.kbs.service.mongo.document.TagClassificationDoc;
import com.digiwin.app.kbs.service.mongo.document.TagDoc;
import com.digiwin.app.kbs.service.mws.biz.TagClassificationBiz;
import com.digiwin.app.kbs.service.mws.dao.mongo.service.ITagClassificationService;
import com.digiwin.app.kbs.service.mws.domain.model.TagClassificationModel;
import com.digiwin.app.kbs.service.mws.domain.model.TagClassificationTreeModel;
import com.digiwin.app.kbs.service.mws.domain.model.TagInfo;
import com.digiwin.app.kbs.service.mws.domain.model.TagTreeModel;
import com.digiwin.app.kbs.service.util.TenantTokenUtil;
import com.digiwin.app.kbs.service.util.TreeUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @ClassName TagClassificationBizImpl
 * @Description 标签类别 逻辑处理 impl
 * @Version: v1-知识库迭代一
 **/
@Service
public class TagClassificationBizImpl implements TagClassificationBiz {

    @Autowired
    ITagClassificationService tagClassificationService;
    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public List<Map<String,Object>> getTagClassificationList(String content) throws Exception{
        Pattern pattern =Pattern.compile("^.*" + content + ".*$",Pattern.CASE_INSENSITIVE);
        Criteria criteria = Criteria.where("tenantsid").is(TenantTokenUtil.getTenantSid());
        criteria.orOperator(Criteria.where("tag_classification_name").is(pattern),Criteria.where("tag_description").is(pattern));
        Query query = new Query(criteria);
        query.with(Sort.by(Sort.Direction.DESC,"create_time"));
        List<TagClassificationDoc> queryList = mongoTemplate.find(query, TagClassificationDoc.class);
        List<Map<String,Object>> resultList = queryList.stream().map(m -> {
            Map<String,Object> map = new HashMap<>();
            map.put("tag_classification_id",m.getObjectId().toString());
            map.put("tag_classification_no",m.getTagClassificationNo());
            map.put("tag_classification_name",m.getTagClassificationName());
            map.put("parent_no",m.getParentNo());
            map.put("relegation_bases",m.getRelegationBases());
            map.put("tag_description",m.getTagDescription());
            map.put("tag_num",CollectionUtils.isEmpty(m.getTagIds()) ? 0: m.getTagIds().size());//统计标签数量
            DateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmmss");
            map.put("create_date",sdf.format(m.getCreateTime()));
            return map;
        }).collect(Collectors.toList());
        return resultList;

    }

    public boolean checkTagClassification(TagClassificationModel tagClassificationModel){
        Query query = Query.query(Criteria.where("tag_classification_no").is(tagClassificationModel.getTagClassificationNo()));
        TagClassificationDoc doc = mongoTemplate.findOne(query, TagClassificationDoc.class);
        if(doc != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public TagClassificationDoc insertTagClassification(TagClassificationModel tagClassificationModel) throws Exception {
        if(checkTagClassification(tagClassificationModel)){
            throw new Exception("标签分类编号已存在");
        }

        // 1 model转entity
        TagClassificationDoc tagClassificationDoc = new TagClassificationDoc();
        BeanUtils.copyProperties(tagClassificationModel,tagClassificationDoc);
        tagClassificationDoc.setRelegationBases(tagClassificationModel.getRelegationBasesInfos().stream().map(o->o.getRelegationBaseName()).collect(Collectors.toList()));
        List<ObjectId> tagIds = new ArrayList<>();
        tagClassificationDoc.setTagIds(tagIds);
        //2 保存标签分类
        mongoTemplate.save(tagClassificationDoc);
        //3 更新知识点
        tagClassificationModel.getRelegationBasesInfos().stream().forEach(o->{
            Update update = new Update();
            update.addToSet("tag_classification_ids").each(tagClassificationDoc.getObjectId());
            mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(o.getRelegationBaseId())), update,"kbs_knowledge_base");
        });

        return tagClassificationDoc;

    }

    @Override
    public void updateTagClassification(TagClassificationModel model) throws Exception {

        //验证编号
        Query query1 = Query.query(Criteria.where("tag_classification_no").is(model.getTagClassificationNo()));
        TagClassificationDoc doc1 = mongoTemplate.findOne(query1, TagClassificationDoc.class);

        if(doc1 != null && !doc1.getObjectId().toString().equals(model.getObjectId())){
            throw new Exception("标签分类编号已存在");
        }

        String objectId = model.getObjectId();
        TagClassificationDoc dbDoc = mongoTemplate.findById(objectId, TagClassificationDoc.class);

        Query query = Query.query(Criteria.where("tag_classification_no").is(model.getTagClassificationNo()));
        TagClassificationDoc doc = mongoTemplate.findOne(query, TagClassificationDoc.class);

        //1 更新知识库对旧的标签分类的引用
        if(!CollectionUtils.isEmpty(dbDoc.getRelegationBases())){
            Update update = new Update();
            update.pull("tag_classification_ids",dbDoc.getObjectId());
            mongoTemplate.updateMulti(Query.query(Criteria.where("base_name").in(dbDoc.getRelegationBases().stream().collect(Collectors.toList()))), update,KnowledgeBaseDoc.class);
        }

        //2 更新知识库对新的标签分类的引用
        if(!CollectionUtils.isEmpty(model.getRelegationBasesInfos())){
            Update update = new Update();
            update.addToSet("tag_classification_ids").each(new ObjectId(model.getObjectId()));
            mongoTemplate.updateMulti(Query.query(Criteria.where("base_name").in(model.getRelegationBasesInfos().stream().map(o->o.getRelegationBaseName()).collect(Collectors.toList()))), update,KnowledgeBaseDoc.class);
        }

        //3 更新标签里面的所属类别集合（todo：暂且按照类别名称唯一来做）
        if(!StringUtils.isEmpty(dbDoc.getTagClassificationName())){
            Update update = new Update();
            update.set("tag_classifications.$",model.getTagClassificationName());
            mongoTemplate.updateMulti(Query.query(Criteria.where("tag_classifications").in(dbDoc.getTagClassificationName())), update, TagDoc.class);
        }

        //4 更新标签分类
        BeanUtils.copyProperties(model,dbDoc,new String[] {"relegationBases","tagIds"});
        dbDoc.setRelegationBases(model.getRelegationBasesInfos().stream().map(m->m.getRelegationBaseName()).collect(Collectors.toList()));
        mongoTemplate.save(dbDoc);

    }
    //递归删除该分类下面的所有子分类
    public void deleteTagClassificationNode(String tagClassificationId) {
        TagClassificationDoc dbDoc = mongoTemplate.findById(new ObjectId(tagClassificationId), TagClassificationDoc.class);

        Query query = Query.query(Criteria.where("parent_no").is(dbDoc.getTagClassificationNo()));
        List<TagClassificationDoc> dbDocList = mongoTemplate.find(query, TagClassificationDoc.class);
        if(!CollectionUtils.isEmpty(dbDocList)){
            dbDocList.stream().forEach(k->{
                //3. 在删子类
                deleteTagClassificationNode(k.getObjectId().toString());
            });
        }

        //2 在知识库里删除标签分类的引用
        if(!CollectionUtils.isEmpty(dbDoc.getRelegationBases())){
            dbDoc.getRelegationBases().stream().forEach(k->{
                Update update = new Update();
                update.pull("tag_classification_ids", new ObjectId(tagClassificationId));
                mongoTemplate.updateMulti(Query.query(Criteria.where("base_name").is(k)), update,KnowledgeBaseDoc.class);
            });
        }

        //3 删除自己
        mongoTemplate.remove(dbDoc);
    }
    public boolean checkTagClassificationBeforeDelete(String tagClassificationId){
        TagClassificationDoc dbDoc = mongoTemplate.findById(new ObjectId(tagClassificationId), TagClassificationDoc.class);
        if(!CollectionUtils.isEmpty(dbDoc.getTagIds())){
            return false;
        }
        Query query = Query.query(Criteria.where("parent_no").is(dbDoc.getTagClassificationNo()));
        List<TagClassificationDoc> dbDocList = mongoTemplate.find(query, TagClassificationDoc.class);
        if(!CollectionUtils.isEmpty(dbDocList)){
            for(int k = 0;k<dbDocList.size();k++){
                if(!CollectionUtils.isEmpty(dbDocList.get(k).getTagIds())){
                    return false;
                }
                checkTagClassificationBeforeDelete(dbDocList.get(k).getObjectId().toString());
            }
        }
        return true;
    }

    @Override
    public void deleteTagClassification(List<TagClassificationModel> modelList) throws Exception {
        if(!CollectionUtils.isEmpty(modelList)){
            for(int i = 0 ; i< modelList.size(); i++){
                if(!checkTagClassificationBeforeDelete(modelList.get(i).getObjectId())){
                    throw new Exception("该类别或者子类别下有标签，不能删除");
                }
            }
        }

        // 逻辑处理
        if(!CollectionUtils.isEmpty(modelList)){
            for(int i = 0 ; i< modelList.size(); i++){
                deleteTagClassificationNode(modelList.get(i).getObjectId());
            }
        }

    }

    @Override
    public void transferTagClassification(TagClassificationModel model) throws Exception {
        TagClassificationDoc dbDoc = mongoTemplate.findById(new ObjectId(model.getObjectId()), TagClassificationDoc.class);

        //1 更新知识库对旧的标签分类的引用
        if(!CollectionUtils.isEmpty(dbDoc.getRelegationBases())){
            Update update = new Update();
            update.pull("tag_classification_ids",dbDoc.getObjectId());
            mongoTemplate.updateMulti(Query.query(Criteria.where("base_name").in(dbDoc.getRelegationBases().stream().collect(Collectors.toList()))), update,KnowledgeBaseDoc.class);

        }
        //2 更新知识库对新的标签分类的引用
        if(!CollectionUtils.isEmpty(model.getRelegationBasesInfos())){
            Update update = new Update();
            update.addToSet("tag_classification_ids").each(new ObjectId(model.getObjectId()));
            mongoTemplate.updateMulti(Query.query(Criteria.where("base_name").in(model.getRelegationBasesInfos().stream().map(o->o.getRelegationBaseName()).collect(Collectors.toList()))), update,KnowledgeBaseDoc.class);
        }

        //3 更新标签分类
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(model.getObjectId()));
        Update update = new Update();
        if(!CollectionUtils.isEmpty(model.getRelegationBasesInfos())){
            update.set("relegation_bases", model.getRelegationBasesInfos().stream().map(o->o.getRelegationBaseName()).collect(Collectors.toList()));
        }else {
            update.set("relegation_bases", new ArrayList<>());
        }
        mongoTemplate.updateFirst(query, update, TagClassificationDoc.class);
    }

    @Override
    public List<TagClassificationTreeModel> getTagClassificationTree(String baseId) {
        if(!StringUtils.isEmpty(baseId)){
            KnowledgeBaseDoc knowledgeBaseDoc = mongoTemplate.findById(new ObjectId(baseId), KnowledgeBaseDoc.class);
            List<ObjectId> classificationIds = knowledgeBaseDoc.getTagClassificationIds();
            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(classificationIds)) {
                Query query = new Query(Criteria.where("_id").in(classificationIds));
                //标签类别列表
                List<TagClassificationDoc> tagClassificationDocList = mongoTemplate.find(query, TagClassificationDoc.class);
                if(!CollectionUtils.isEmpty(tagClassificationDocList)){
                    List<TagClassificationTreeModel> treeModelList = tagClassificationDocList.stream().map(m -> new TagClassificationTreeModel(m.getTagClassificationNo(),m.getParentNo(),m.getObjectId().toString(),m.getTagClassificationNo(),m.getTagClassificationName())).collect(Collectors.toList());
                    List<TagClassificationTreeModel> trees = TreeUtil.bulid(treeModelList, "", null);
                    return trees;
                }
            }
            return new ArrayList<>();
        }else {
            Criteria criteria = new Criteria();
            criteria.and("tenantsid").is(TenantTokenUtil.getTenantSid());
            List<TagClassificationDoc> all = mongoTemplate.find(new Query(criteria), TagClassificationDoc.class);
            List<TagClassificationTreeModel> treeModelList = all.stream().map(m -> new TagClassificationTreeModel(m.getTagClassificationNo(),m.getParentNo(),m.getObjectId().toString(),m.getTagClassificationNo(),m.getTagClassificationName())).collect(Collectors.toList());
            List<TagClassificationTreeModel> trees = TreeUtil.bulid(treeModelList, "", null);
            return trees;
        }

    }
    @Override
    public List<TagTreeModel> getTagTree(String baseId) {
        //知识点
        KnowledgeBaseDoc knowledgeBaseDoc = mongoTemplate.findById(new ObjectId(baseId), KnowledgeBaseDoc.class);
        List<ObjectId> classificationIds = knowledgeBaseDoc.getTagClassificationIds();
        List<Map<String, Object>> mapTagClassificationList = new ArrayList<>();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(classificationIds)) {
            Query query = new Query(Criteria.where("_id").in(classificationIds));
            //标签类别列表
            List<TagClassificationDoc> tagClassificationDocList = mongoTemplate.find(query, TagClassificationDoc.class);
            for (TagClassificationDoc tagClassificationDoc : tagClassificationDocList) {
                Map<String, Object> mapTagClassification = new HashMap<>();
                List<ObjectId> tagIds = tagClassificationDoc.getTagIds();
                List<TagDoc> tagDocList = new ArrayList<>();
                if (!CollectionUtils.isEmpty(tagIds)) {
                    Query queryTag = new Query(Criteria.where("_id").in(tagIds));
                    //标签列表
                    tagDocList = mongoTemplate.find(queryTag, TagDoc.class);
                }
                mapTagClassification.put("tag_classification_id", tagClassificationDoc.getObjectId().toString());
                mapTagClassification.put("tag_classification_no", tagClassificationDoc.getTagClassificationNo());
                mapTagClassification.put("tag_classification_name", tagClassificationDoc.getTagClassificationName());
                mapTagClassification.put("tag_classification_parent_no", tagClassificationDoc.getParentNo());
                mapTagClassification.put("tag_info",Optional.ofNullable(tagDocList).orElseGet(() -> {
                        return new ArrayList<>();
                        }).stream().map(m -> {
                            TagInfo tagInfo = new TagInfo();
                            tagInfo.setTag_id(m.getObjectId().toString());
                            tagInfo.setTag_name(m.getTagName());
                            return tagInfo;
                        }).collect(Collectors.toList()));
                mapTagClassificationList.add(mapTagClassification);

            }

        }
        List<TagTreeModel> treeModelList = mapTagClassificationList.stream().map(m -> new TagTreeModel(m.get("tag_classification_no").toString(), m.get("tag_classification_parent_no").toString(), m.get("tag_classification_id").toString(), m.get("tag_classification_no").toString(), m.get("tag_classification_name").toString(), (List<TagInfo>)m.get("tag_info"))).collect(Collectors.toList());
        List<TagTreeModel> trees = TreeUtil.bulid(treeModelList, "", null);
        return trees;
    }
}
