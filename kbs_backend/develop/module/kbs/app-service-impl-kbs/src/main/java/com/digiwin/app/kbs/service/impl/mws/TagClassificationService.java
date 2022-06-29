package com.digiwin.app.kbs.service.impl.mws;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.digiwin.app.kbs.service.mongo.document.TagClassificationDoc;
import com.digiwin.app.kbs.service.mongo.document.TagDoc;
import com.digiwin.app.kbs.service.util.BizUtil;
import com.digiwin.app.kbs.service.mws.biz.TagBiz;
import com.digiwin.app.kbs.service.mws.biz.TagClassificationBiz;
import com.digiwin.app.kbs.service.mws.domain.model.TagClassificationModel;
import com.digiwin.app.kbs.service.mws.domain.model.TagClassificationTreeModel;
import com.digiwin.app.kbs.service.mws.domain.model.TagModel;
import com.digiwin.app.kbs.service.mws.domain.model.TagTreeModel;
import com.digiwin.app.kbs.service.mws.restful.ITagClassificationService;
import com.digiwin.app.service.DWServiceResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TagClassificationService
 * @Description 标签类别
 * @Author huly
 * @Date 2022/1/20 18:11
 * @Version 1.0
 **/
public class TagClassificationService implements ITagClassificationService {

    @Autowired
    TagClassificationBiz tagClassificationBiz;

    @Autowired
    TagBiz tagBiz;

    @Override
    public DWServiceResult getTagClassificationList(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            // 接收入参，转model
            JSONObject jsonObject = JSONObject.parseObject(messageBody);
            JSONArray queryInfo = (JSONArray) jsonObject.get("tag_classification_info");
            JSONObject param = (JSONObject)queryInfo.get(0);
            List<Map<String,Object>> list = tagClassificationBiz.getTagClassificationList(param.getString("search_content"));
            Map<String, Object> map = new HashMap<>();
            map.put("tag_classification_info",list);
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
    public Object getTagClassificationTree(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            // 接收入参，转model
            JSONObject jsonObject = JSONObject.parseObject(messageBody);
            JSONArray queryInfo = (JSONArray) jsonObject.get("query_info");
            JSONObject param = (JSONObject)queryInfo.get(0);
            List<TagClassificationTreeModel> resultList = tagClassificationBiz.getTagClassificationTree(param.get("base_id").toString());
            Map<String, Object> map = new HashMap<>();
            map.put("tag_classification_info",resultList);
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
    public Object getTagTree(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            // 接收入参，转model
            JSONObject jsonObject = JSONObject.parseObject(messageBody);
            JSONArray queryInfo = (JSONArray) jsonObject.get("query_info");
            JSONObject param = (JSONObject)queryInfo.get(0);

            List<TagTreeModel> resultList = tagClassificationBiz.getTagTree(param.get("base_id").toString());
            Map<String, Object> map = new HashMap<>();
            map.put("tag_classification_info",resultList);
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
    public DWServiceResult insertTagClassification(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            List<TagClassificationModel> modelList = BizUtil.getKnowledgeReqBizParam(messageBody, "tag_classification_info", TagClassificationModel.class);
            TagClassificationDoc doc = tagClassificationBiz.insertTagClassification(modelList.get(0));
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> map1 = new HashMap<>();
            map1.put("tag_classification_id", doc.getObjectId().toString());
            map.put("tag_classification_info", map1);
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
    public DWServiceResult updateTagClassification(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            List<TagClassificationModel> modelList = BizUtil.getKnowledgeReqBizParam(messageBody, "tag_classification_info", TagClassificationModel.class);
            tagClassificationBiz.updateTagClassification(modelList.get(0));
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
    public DWServiceResult deleteTagClassification(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            List<TagClassificationModel> modelList = BizUtil.getKnowledgeReqBizParam(messageBody, "tag_classification_info", TagClassificationModel.class);
            tagClassificationBiz.deleteTagClassification(modelList);

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
    public DWServiceResult transferTagClassification(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            List<TagClassificationModel> modelList = BizUtil.getKnowledgeReqBizParam(messageBody, "tag_classification_info", TagClassificationModel.class);
            for(int i = 0 ; i< modelList.size() ; i++){
                tagClassificationBiz.transferTagClassification(modelList.get(i));
            }
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
    public DWServiceResult getTagList(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            // 接收入参，转model
            JSONObject jsonObject = JSONObject.parseObject(messageBody);
            JSONArray queryInfo = (JSONArray) jsonObject.get("tag_info");
            JSONObject param = (JSONObject)queryInfo.get(0);
            List<Map<String, Object>> jsonArray = tagBiz.getTagList(param);
            Map<String, Object> map = new HashMap<>();
            map.put("tag_classification_info",jsonArray);
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
    public DWServiceResult insertTag(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            List<TagModel> modelList = BizUtil.getKnowledgeReqBizParam(messageBody, "tag_info", TagModel.class);
            TagDoc doc = tagBiz.insertTag(modelList.get(0));
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> map1 = new HashMap<>();
            map1.put("tag_id", doc.getObjectId().toString());
            map.put("tag_classification_info", map1);
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
    public DWServiceResult updateTag(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            List<TagModel> modelList = BizUtil.getKnowledgeReqBizParam(messageBody, "tag_info", TagModel.class);
            tagBiz.updateTag(modelList.get(0));
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
    public DWServiceResult updateTagStatus(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            List<TagModel> modelList = BizUtil.getKnowledgeReqBizParam(messageBody, "tag_info", TagModel.class);
            List<TagDoc> docList = BizUtil.convertListWithFunc(modelList, TagDoc.class, (obj, tagDoc) -> {
                TagModel m = (TagModel) obj;
                tagDoc.setObjectId(new ObjectId(m.getObjectId()));
            });
            tagBiz.updateTagStatus(docList);
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
    public DWServiceResult deleteTag(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            List<TagModel> modelList = BizUtil.getKnowledgeReqBizParam(messageBody, "tag_info", TagModel.class);
            List<TagDoc> docList = BizUtil.convertListWithFunc(modelList, TagDoc.class, (obj, tagDoc) -> {
                TagModel m = (TagModel) obj;
                tagDoc.setObjectId(new ObjectId(m.getObjectId()));
            });
            tagBiz.deleteTag(docList);


            /*List<TagModel> modelList = BizUtil.getKnowledgeReqBizParam(messageBody, "tag_info", TagModel.class);
            tagBiz.deleteTag(modelList.get(0).getObjectId());*/
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
    public DWServiceResult copyTransferTag(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            // 接收入参，转model
            List<TagModel> modelList = BizUtil.getKnowledgeReqBizParam(messageBody, "tag_info", TagModel.class);

            for(int i = 0 ; i< modelList.size();i++){
                tagBiz.copyTransferTag(modelList.get(i));
            }
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
