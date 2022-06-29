package com.digiwin.app.kbs.service.mws.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.digiwin.app.kbs.service.mongo.document.TagClassificationDoc;
import com.digiwin.app.kbs.service.mongo.document.TagDoc;
import com.digiwin.app.kbs.service.mws.biz.TagBiz;
import com.digiwin.app.kbs.service.mws.domain.model.TagClassificationInfoModel;
import com.digiwin.app.kbs.service.mws.domain.model.TagModel;
import com.digiwin.app.service.DWServiceContext;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @ClassName TagBizImpl
 * @Description 标签 逻辑处理 impl
 * @Version: v1-知识库迭代一
 **/
@Service
public class TagBizImpl implements TagBiz {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Date strToDateLong(String strDate) {

        Date strtodate = null;

        try {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            strtodate = formatter.parse(strDate);

        } catch (ParseException e) {

            e.printStackTrace();

        }

        return strtodate;
    }

    @Override
    public List<Map<String, Object>> getTagList(JSONObject param) {
        Query query = new Query();
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "create_time")));
        Criteria criteria = new Criteria();
        Map<String, Object> profile = DWServiceContext.getContext().getProfile();
        Long tenantsid = (Long) profile.get("tenantSid");
        if (tenantsid != 0) {
            criteria.and("tenantsid").is(tenantsid);
        }
        if (!StringUtils.isEmpty(param.get("tag_classification_id"))) {
            criteria.and("tag_classifications.tag_classification_id").is(new ObjectId(param.get("tag_classification_id").toString()));
        }
        if (!StringUtils.isEmpty(param.get("manage_status"))) {
            criteria.and("manage_status").is(param.get("manage_status"));
        }
        if (StringUtils.isEmpty(param.get("start_time")) && !StringUtils.isEmpty(param.get("end_time"))) {
            criteria.and("create_time").lte(strToDateLong(param.get("end_time").toString()));
        }
        if (!StringUtils.isEmpty(param.get("start_time")) && StringUtils.isEmpty(param.get("end_time"))) {
            criteria.and("create_time").gte(strToDateLong(param.get("start_time").toString()));
        }
        if (!StringUtils.isEmpty(param.get("start_time")) && !StringUtils.isEmpty(param.get("end_time"))) {
            criteria.and("create_time").gte(strToDateLong(param.get("start_time").toString())).lte(strToDateLong(param.get("end_time").toString()));
        }
        if (!StringUtils.isEmpty(param.get("search_content"))) {
            Pattern pattern = Pattern.compile("^.*" + param.get("search_content") + ".*$", Pattern.CASE_INSENSITIVE);
            criteria.orOperator(Criteria.where("tag_name").is(pattern), Criteria.where("tag_description").is(pattern));
        }
        query.addCriteria(criteria);
        List<TagDoc> queryList = mongoTemplate.find(query, TagDoc.class);
        List<Map<String, Object>> resultList = queryList.stream().map(m -> {
            Map<String, Object> map = new HashMap<>();
            map.put("tag_id", m.getObjectId().toString());
            map.put("tag_name", m.getTagName());
            map.put("tag_description", m.getTagDescription());
            map.put("tag_classification_info", Optional.ofNullable(m.getTagClassifications()).orElseGet(() -> {
                        return new ArrayList<>();
                    }).stream().filter(Objects::nonNull).map(k->{
                        String tagClassificationName = k.getTagClassificationName();
                        String tagClassificationId = k.getTagClassificationId();
                        Map<String,String> data = new HashMap<>();
                        data.put("tag_classification_id",tagClassificationId);
                        data.put("tag_classification_name",tagClassificationName);
                        return data;
                    }
                    ).collect(Collectors.toList()));
            map.put("citations", m.getCitations());
            map.put("manage_status", m.getManageStatus());//统计标签数量
            return map;
        }).collect(Collectors.toList());
        return resultList;
    }


    /**
     * 验证标签名称是否存在
     * @param tagModel
     * @return
     */
    public boolean checkTag(TagModel tagModel){
        Query query = Query.query(Criteria.where("tag_name").is(tagModel.getTagName()));
        TagClassificationDoc doc = mongoTemplate.findOne(query, TagClassificationDoc.class);
        if(doc != null){
            return true;
        }else {
            return false;
        }
    }
    @Override
    public TagDoc insertTag(TagModel tagModel) throws Exception {
        /*if(checkTag(tagModel)){
            throw new Exception("标签名称已存在");
        }*/

        // 1 model转entity
        TagDoc tagDoc = new TagDoc();
        BeanUtils.copyProperties(tagModel,tagDoc);
        tagDoc.setCitations(0);
        //2 保存标签
        mongoTemplate.save(tagDoc);
        //3 更新标签里面的分类
        if(!CollectionUtils.isEmpty(tagModel.getTagClassifications())){
            Update update = new Update();
            update.set("tag_classifications", Optional.ofNullable(tagModel.getTagClassifications()).orElseGet(() -> {
                return new ArrayList<>();
            }).stream().map(m-> {
                Map<String ,Object> map = new HashMap<>();
                map.put("tag_classification_id",new ObjectId(m.getTagClassificationId()));
                map.put("tag_classification_name",m.getTagClassificationName());
                return map;
            }).collect(Collectors.toList()));
            mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(tagDoc.getObjectId())), update,TagDoc.class);
        }


        //3 更新标签类别表
        if(!CollectionUtils.isEmpty(tagModel.getTagClassifications())){
            tagModel.getTagClassifications().stream().forEach(o->{
                Update update = new Update();
                update.addToSet("tag_ids").each(new ObjectId(tagDoc.getObjectId().toString()));
                mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(o.getTagClassificationId())), update,"kbs_tag_classification");
            });
        }

        return tagDoc;

    }

    @Override
    public void updateTag(TagModel tagModel) throws Exception {
        TagDoc dbTagDoc = mongoTemplate.findById(new ObjectId(tagModel.getObjectId()), TagDoc.class);
        //1 旧类别更新
        if(!CollectionUtils.isEmpty(dbTagDoc.getTagClassifications())){
            Update update = new Update();
            update.pull("tag_ids",new ObjectId(tagModel.getObjectId()));
            mongoTemplate.updateMulti(Query.query(Criteria.where("_id").in(dbTagDoc.getTagClassifications().stream().map(o->o.getTagClassificationId()).collect(Collectors.toList()))), update,TagClassificationDoc.class);

        }
        //2 新类别更新
        if(!CollectionUtils.isEmpty(tagModel.getTagClassifications())){
            Update update = new Update();
            update.addToSet("tag_ids").each(new ObjectId(tagModel.getObjectId()));
            mongoTemplate.updateMulti(Query.query(Criteria.where("_id").in(tagModel.getTagClassifications().stream().map(m->m.getTagClassificationId()).collect(Collectors.toList()))), update,TagClassificationDoc.class);
        }

        //3 更新标签
        /*dbTagDoc.setTagClassifications(tagModel.getTagClassifications());
        dbTagDoc.setTagDescription(tagModel.getTagDescription());
        dbTagDoc.setManageStatus(tagModel.getManageStatus());
        mongoTemplate.save(dbTagDoc);*/

        Update update = new Update();
        update.set("manage_status",tagModel.getManageStatus());
        update.set("tag_name",tagModel.getTagName());
        update.set("tag_description",tagModel.getTagDescription());
        update.set("tag_classifications", Optional.ofNullable(tagModel.getTagClassifications()).orElseGet(() -> {
            return new ArrayList<>();
        }).stream().map(m-> {
            Map<String ,Object> map = new HashMap<>();
            map.put("tag_classification_id",new ObjectId(m.getTagClassificationId()));
            map.put("tag_classification_name",m.getTagClassificationName());
            return map;
        }).collect(Collectors.toList()));
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(new ObjectId(tagModel.getObjectId()))), update,TagDoc.class);

    }

    @Override
    public void updateTagStatus(List<TagDoc> docList) throws Exception {
        for (TagDoc doc : docList) {
            Update update = new Update();
            update.set("manage_status",doc.getManageStatus());
            mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(doc.getObjectId())), update,TagDoc.class);
        }
    }
    @Override
    public void deleteTag(List<TagDoc> docList) throws Exception {
        //校验
        for (TagDoc tagDoc : docList){
            TagDoc dbTagDoc = mongoTemplate.findById(tagDoc.getObjectId(), TagDoc.class);
            tagDoc.setTagClassifications(dbTagDoc.getTagClassifications());
            //1 使用中的标签不可删除（引用次数 > 0）
            if(dbTagDoc.getCitations() > 0){
                throw new Exception("使用中的标签不可删除（引用次数 > 0）");
            }
        }

        for (TagDoc tagDoc : docList){
            //1 更新旧标签类别对标签的关联
            if(!CollectionUtils.isEmpty(tagDoc.getTagClassifications())){
                Update update = new Update();
                update.pull("tag_ids",tagDoc.getObjectId());
                mongoTemplate.updateMulti(Query.query(Criteria.where("_id").in(tagDoc.getTagClassifications().stream().map(o->o.getTagClassificationId()).collect(Collectors.toList()))), update,TagClassificationDoc.class);
            }

            //2 删除标签
            TagDoc doc = new TagDoc();
            doc.setObjectId(tagDoc.getObjectId());
            mongoTemplate.remove(doc);
        }
    }

    @Override
    public void copyTransferTag(TagModel tagModel) throws Exception {

        String transferFlag= tagModel.getTransferFlag();
        if(StringUtils.isEmpty(transferFlag)){
            throw new Exception("转移标识不能为空");
        }
        if("1".equals(transferFlag)){   //转移
            transferTag(tagModel);
        }else { // 复制
            copyTag(tagModel);
        }

    }

    /**
     * 复制标签
     */
    private void copyTag(TagModel tagModel){
        TagDoc dbTagDoc = mongoTemplate.findById(new ObjectId(tagModel.getObjectId()), TagDoc.class);
        //1 新类别更新
        if(!CollectionUtils.isEmpty(tagModel.getTagClassifications())){
            Update update = new Update();
            update.addToSet("tag_ids").each(new ObjectId(tagModel.getObjectId()));
            mongoTemplate.updateMulti(Query.query(Criteria.where("_id").in(tagModel.getTagClassifications().stream().map(m->new ObjectId(m.getTagClassificationId())).collect(Collectors.toList()))), update,TagClassificationDoc.class);
        }

        //3 更新标签
        Update update = new Update();
        update.set("tag_classifications", Optional.ofNullable(addList(dbTagDoc.getTagClassifications(),tagModel.getTagClassifications())).orElseGet(() -> {
            return new ArrayList<>();
        }).stream().map(m-> {
            Map<String ,Object> map = new HashMap<>();
            map.put("tag_classification_id",new ObjectId(m.getTagClassificationId()));
            map.put("tag_classification_name",m.getTagClassificationName());
            return map;
        }).collect(Collectors.toList()));
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(new ObjectId(tagModel.getObjectId()))), update,TagDoc.class);
    }


    /**
     * list 合并 并去重
     * @param listA
     * @param listB
     * @return
     */
    public List<TagClassificationInfoModel> addList(List<TagClassificationInfoModel> listA, List<TagClassificationInfoModel> listB){
        Map<String,TagClassificationInfoModel> target = new HashMap<>();
        if(!CollectionUtils.isEmpty(listA) && CollectionUtils.isEmpty(listB)){
            for(TagClassificationInfoModel model : listA){
                target.put(model.getTagClassificationId(),model);
            }
        }
        if(CollectionUtils.isEmpty(listA) && !CollectionUtils.isEmpty(listB)){
            for(TagClassificationInfoModel model : listB){
                target.put(model.getTagClassificationId(),model);
            }
        }
        if(!CollectionUtils.isEmpty(listA) || !CollectionUtils.isEmpty(listB)){
            for(TagClassificationInfoModel model : listA){
                target.put(model.getTagClassificationId(),model);
            }
            for(TagClassificationInfoModel model : listB){
                if(!target.containsKey(model.getTagClassificationId())){
                    target.put(model.getTagClassificationId(),model);
                }
            }
        }

        List<TagClassificationInfoModel> list = new ArrayList<>(target.values());

        return list;
    }
    /**
     * 转移标签
     */
    private void transferTag(TagModel tagModel){
        TagDoc dbTagDoc = mongoTemplate.findById(new ObjectId(tagModel.getObjectId()), TagDoc.class);
        //1 更新旧的标签分类对标签的引用
        if(!CollectionUtils.isEmpty(dbTagDoc.getTagClassifications())){

            Update update = new Update();
            update.pull("tag_ids",new ObjectId(tagModel.getObjectId()));
            mongoTemplate.updateMulti(Query.query(Criteria.where("_id").in(dbTagDoc.getTagClassifications().stream().map(o->new ObjectId(o.getTagClassificationId())).collect(Collectors.toList()))), update,TagClassificationDoc.class);
        }

        //2 新类别更新
        if(!CollectionUtils.isEmpty(tagModel.getTagClassifications())){
            Update update = new Update();
            update.addToSet("tag_ids").each(new ObjectId(tagModel.getObjectId()));
            mongoTemplate.updateMulti(Query.query(Criteria.where("_id").in(tagModel.getTagClassifications().stream().map(m->new ObjectId(m.getTagClassificationId())).collect(Collectors.toList()))), update,TagClassificationDoc.class);
        }

        //3 更新标签
        Update update = new Update();
        update.set("tag_classifications",Optional.ofNullable(tagModel.getTagClassifications()).orElseGet(() -> {
            return new ArrayList<>();
        }).stream().map(m-> {
            Map<String ,Object> map = new HashMap<>();
            map.put("tag_classification_id",new ObjectId(m.getTagClassificationId()));
            map.put("tag_classification_name",m.getTagClassificationName());
            return map;
        }).collect(Collectors.toList()));
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(new ObjectId(tagModel.getObjectId()))), update,TagDoc.class);
    }
}
