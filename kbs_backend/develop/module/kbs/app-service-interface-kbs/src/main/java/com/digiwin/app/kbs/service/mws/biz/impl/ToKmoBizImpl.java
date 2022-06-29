package com.digiwin.app.kbs.service.mws.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.digiwin.app.kbs.service.mws.biz.ToKmoBiz;
import com.digiwin.app.kbs.service.mws.domain.model.toKmo.KnoHomeModel;
import com.digiwin.app.kbs.service.mws.domain.model.toKmo.KnoInfoGetModel;
import com.digiwin.app.kbs.service.util.FrcJsonHandleTool;
import com.digiwin.app.kbs.service.util.KmoTool;
import com.digiwin.app.kbs.service.util.RequestTool;
import com.digiwin.app.kbs.service.util.TenantTokenUtil;
import com.github.stuxuhai.jpinyin.ChineseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ToKmoBizImpl
 * @Description 与kmo交互逻辑处理
 * @Version 1.1
 **/
@Service
public class ToKmoBizImpl implements ToKmoBiz {
    @Autowired
    private FrcJsonHandleTool frcJsonHandleTool;
    @Override
    public JSONObject getKnowledgeBaseHome(KnoHomeModel knoHomeModel) throws Exception {
        String param = processRequestParam(knoHomeModel);
        JSONObject result = new JSONObject();
        // todo token待处理，目前假数据
        JSONObject responseParam = RequestTool.request(KmoTool.getKmoUrl("kmoBaseHomeUrl"), TenantTokenUtil.getUserToken(), param, JSONObject.class);
        result = processResponse(responseParam);
        return result;
    }

    @Override
    public JSONObject getKnowledgeInfo(KnoInfoGetModel knoInfoGetModel) throws Exception {
        // 封装要请求kmo的入参
        String param = processRequestParamForGet(knoInfoGetModel);
        JSONObject result = new JSONObject();
        // 获取返回数据
        JSONObject responseParam = RequestTool.request(KmoTool.getKmoUrl("kmoKnowledgeList"), TenantTokenUtil.getUserToken(), param, JSONObject.class);
        // 将kmo返回数据 转换为 kbs需要的返回数据
        result = processResponseForGet(responseParam);
        return result;
    }

    @Override
    public JSONObject knowledgeDetail(String param) throws Exception {
        JSONObject result = new JSONObject();
        // 获取返回数据
        String url = KmoTool.getKmoUrl("kmoDetail");
        JSONObject paramJson = JSON.parseObject(param);
        String language = paramJson.getString("language");
        paramJson.remove("language");
        JSONObject response = RequestTool.request(url, TenantTokenUtil.getUserToken(), paramJson.toJSONString(), JSONObject.class);
        // 将kmo返回数据 转换为 kbs需要的返回数据
//        result = processResponseForDetail(response);
        result = frcMessageHandle(response,language);


        return result;
    }



    @Override
    public JSONObject transferKnowledgeInfoCopy(String param) throws Exception {
        JSONObject result = new JSONObject();
        // 获取返回数据
        String url = KmoTool.getKmoUrl("kmoCopyTransfer");
        result = RequestTool.request(url, TenantTokenUtil.getUserToken(), param, JSONObject.class);

        return result;
    }

    @Override
    public JSONObject deleteKnowledgeInfoDelete(String param) throws Exception {
        // 获取返回数据
        String url = KmoTool.getKmoUrl("kmoDelete");
        return RequestTool.request(url, TenantTokenUtil.getUserToken(), param, JSONObject.class);
    }

    @Override
    public JSONObject updateKnowledgeInfo(String param) throws Exception {
        JSONObject result = new JSONObject();
        // 获取返回数据
        String url = KmoTool.getKmoUrl("kmoTagUpdate");
        result = RequestTool.request(url, TenantTokenUtil.getUserToken(), param, JSONObject.class);
        return result;
    }

    @Override
    public JSONObject getFileDetail(String param) throws Exception {
        JSONObject result = new JSONObject();
        // 获取返回数据
        String url = KmoTool.getKmoUrl("kmoFileDetail");
        result = RequestTool.request(url, TenantTokenUtil.getUserToken(), param, JSONObject.class);
        return result;
    }

    /**
     * 获取搜索提示
     *
     * @param messageBody
     * @return
     */
    @Override
    public JSONObject searchTips(String messageBody) throws Exception {
        JSONObject result = new JSONObject();
        // 获取返回数据
        String url = KmoTool.getKmoUrl("kmoSearchTips");
        result = RequestTool.request(url, TenantTokenUtil.getUserToken(), messageBody, JSONObject.class);
        return result;
    }
    /**
     * 获取知识中台地端url
     *
     * @param messageBody
     * @return
     */
    @Override
    public JSONObject getKmoLocalUrl(String messageBody) throws Exception {
        JSONObject result = new JSONObject();
        // 获取返回数据
        String url = KmoTool.getKmoUrl("kmoLocalUrl");
        result = RequestTool.request(url, TenantTokenUtil.getUserToken(), messageBody, JSONObject.class);
        return result;
    }
    /**
     * 知识库首页检索-封装请求kmo参数
     *
     * @param knoHomeModel 知识库请求入参
     * @return kno请求入参
     */
    private String processRequestParam(KnoHomeModel knoHomeModel) {
        JSONObject param = new JSONObject();
        // 搜索内容，必传
        param.put("search_content", knoHomeModel.getSearchContent());
        param.put("search_range", knoHomeModel.getSearchRange());
        param.put("knowledge_classification_no", knoHomeModel.getKnowledgeClassificationNo());
        param.put("tag_classification_no", knoHomeModel.getTagClassificationNo());
        param.put("start_time", knoHomeModel.getStartTime());
        param.put("end_time", knoHomeModel.getEndTime());
        // 分页信息
        JSONObject pagination = new JSONObject();
        pagination.put("page", knoHomeModel.getPagination().getPage());
        pagination.put("page_size", knoHomeModel.getPagination().getPageSize());
        param.put("pagination", pagination);
        return param.toJSONString();
    }

    /**
     * 封装kmo 返回信息
     *
     * @param kmoResult kmo request message
     * @return kbs need param
     */
    private JSONObject processResponse(JSONObject kmoResult) {
        // 获取最外层数据
        JSONArray knowledgeInfos = kmoResult.getJSONArray("knowledge_info");
        if (knowledgeInfos.size() == 0) {
            return null;
        }
        // 组装返回参数
        List<JSONObject> objectList = new ArrayList<>();
        knowledgeInfos.stream().forEach(e -> {
            JSONObject knowledge = new JSONObject();
            JSONObject kmoResponseParam = (JSONObject) e;
            if ("knowledge".equals(kmoResponseParam.getString("data_type"))) {
                knowledge.put("data_type", "knowledge");
                knowledge.put("knowledge_id", kmoResponseParam.getString("id"));
                knowledge.put("knowledge_title", kmoResponseParam.getString("knowledge_name"));
                knowledge.put("knowledge_content", kmoResponseParam.getString("knowledge_desc"));
                knowledge.put("highlight_title", kmoResponseParam.getJSONObject("highlight_info").get("knowledge_name"));
                knowledge.put("highlight_content", kmoResponseParam.getJSONObject("highlight_info").get("knowledge_desc"));
                knowledge.put("knowledge_author", kmoResponseParam.getString("create_name"));
                knowledge.put("release_time", kmoResponseParam.getString("create_time"));
                knowledge.put("read_num", kmoResponseParam.getInteger("doc_view_number"));
                // 获取来源
                knowledge.put("knowledge_source", kmoResponseParam.get("knowledge_source"));
                knowledge.put("knowledge_base", kmoResponseParam.get("knowledge_base"));
                if (kmoResponseParam.containsKey("knowledge_tag")) {
                    List<JSONObject> tagMessage = kmoResponseParam.getJSONArray("knowledge_tag").stream().map(m -> {
                        JSONObject tag = new JSONObject();
                        JSONObject kmoResponseTag = (JSONObject) m;
                        tag.put("knowledge_tag_id", kmoResponseTag.getString("id"));
                        tag.put("knowledge_tag_name", StringUtils.isEmpty(kmoResponseTag.getString("tag_name")) ? kmoResponseTag.getString("tagName") : kmoResponseTag.getString("tag_name"));
                        return tag;
                    }).collect(Collectors.toList());
                    knowledge.put("knowledge_tag_info", tagMessage);
                }
            }
            if ("doc".equals(kmoResponseParam.getString("data_type"))) {
                knowledge.put("data_type", "doc");
                knowledge.put("knowledge_id", kmoResponseParam.getString("id"));
                knowledge.put("knowledge_title", kmoResponseParam.getString("file_name"));
                knowledge.put("knowledge_content", kmoResponseParam.getString("file_desc"));
                knowledge.put("highlight_title", kmoResponseParam.getJSONObject("highlight_info").get("file_name"));
                knowledge.put("highlight_content", kmoResponseParam.getJSONObject("highlight_info").get("file_desc"));
                knowledge.put("knowledge_author", kmoResponseParam.getString("create_name"));
                knowledge.put("release_time", kmoResponseParam.getString("create_time"));
                knowledge.put("read_num", kmoResponseParam.getInteger("doc_view_number"));
                // 获取来源
                knowledge.put("knowledge_source", kmoResponseParam.get("file_source"));
                knowledge.put("knowledge_base", kmoResponseParam.get("knowledge_base"));
                if (kmoResponseParam.containsKey("file_tag")) {
                    List<JSONObject> tagMessage = kmoResponseParam.getJSONArray("file_tag").stream().map(m -> {
                        JSONObject tag = new JSONObject();
                        JSONObject kmoResponseTag = (JSONObject) m;
                        tag.put("knowledge_tag_id", kmoResponseTag.getString("id"));
                        tag.put("knowledge_tag_name", StringUtils.isEmpty(kmoResponseTag.getString("tag_name")) ? kmoResponseTag.getString("tagName") : kmoResponseTag.getString("tag_name"));
                        return tag;
                    }).collect(Collectors.toList());
                    knowledge.put("knowledge_tag_info", tagMessage);
                }
            }
            objectList.add(knowledge);
        });
        JSONObject response = new JSONObject();
        response.put("knowledge_info", objectList);
        response.put("pagination", kmoResult.getJSONObject("pagination"));
        return response;
    }

    /**
     * 获取知识清单-封装请求入参
     *
     * @param knoInfoGetModel 获取知识清单-请求入参
     * @return kno请求入参-获取知识清单
     */
    private String processRequestParamForGet(KnoInfoGetModel knoInfoGetModel) {
        JSONObject param = new JSONObject();
        // 搜索内容，必传
        param.put("classification_id", knoInfoGetModel.getClassificationId());
        param.put("base_no", knoInfoGetModel.getBaseNo());
        param.put("search_content", knoInfoGetModel.getSearchContent());
        param.put("start_time", knoInfoGetModel.getStartTime());
        param.put("end_time", knoInfoGetModel.getEndTime());
        param.put("manage_status", knoInfoGetModel.getManageStatus());
        // 分页信息
        JSONObject pagination = new JSONObject();
        pagination.put("page", knoInfoGetModel.getPagination().getPage());
        pagination.put("page_size", knoInfoGetModel.getPagination().getPageSize());
        param.put("pagination", pagination);
        return param.toJSONString();
    }

    /**
     * 获取知识清单-封装kmo 返回信息
     *
     * @param kmoResult kmo request message
     * @return kbs need param
     */
    private JSONObject processResponseForGet(JSONObject kmoResult) {
        // 获取最外层数据
        JSONArray knowledgeInfos = kmoResult.getJSONArray("knowledge_info");
        if (knowledgeInfos.size() == 0) {
            return null;
        }
        // 组装返回参数
        List<JSONObject> objectList = knowledgeInfos.stream().map(e -> {
            JSONObject knowledge = new JSONObject();
            JSONObject kmoResponseParam = (JSONObject) e;
            knowledge.put("knowledge_id", kmoResponseParam.getString("id"));
            knowledge.put("knowledge_no", kmoResponseParam.getString("knowledge_no"));
            knowledge.put("knowledge_name", kmoResponseParam.getString("knowledge_name"));
            knowledge.put("knowledge_desc", kmoResponseParam.getString("knowledge_desc"));
            knowledge.put("knowledge_type", kmoResponseParam.getJSONArray("knowledge_type"));
            knowledge.put("is_release", kmoResponseParam.getBooleanValue("doc_state"));
            knowledge.put("read_num", kmoResponseParam.getString("doc_view_number"));
            knowledge.put("knowledge_source", kmoResponseParam.getJSONArray("knowledge_source"));
            // 加入标签信息
            knowledge.put("tag_info", kmoResponseParam.get("knowledge_tag"));
            // 加入分类
            knowledge.put("knowledge_type", kmoResponseParam.get("knowledge_type"));
            return knowledge;
        }).collect(Collectors.toList());
        JSONObject response = new JSONObject();
        response.put("knowledge_info", objectList);
        response.put("pagination", kmoResult.getJSONObject("pagination"));
        return response;
    }

    /**
     * 获取知识清单-封装kmo 返回信息-动态生成解决方案
     * @param kmoResult
     * @return
     */
    private JSONObject frcMessageHandle(JSONObject kmoResult,String language) {
        JSONArray knowledgeSource = kmoResult.getJSONArray("knowledge_source");
        if (!knowledgeSource.contains("FRC")) {
            return kmoResult;
        }
        JSONObject knowledgeCustomize = kmoResult.getJSONObject("knowledge_customize");
        JSONArray solutionModule = new JSONArray();
        // 处理不同的解决方案
        switch (knowledgeCustomize.getString("solution_name")) {
            case "一般解决方案":
                solutionModule = frcJsonHandleTool.commonSolutModule(knowledgeCustomize);

                break;
            case "8D解决方案":
                solutionModule = frcJsonHandleTool.edSolutModule(knowledgeCustomize);
                break;
            case "通用解决方案":
                solutionModule = frcJsonHandleTool.currencySolutModule(knowledgeCustomize);
                break;
            default:
                break;
        }
        JSONObject newKnowledgeCustomize = new JSONObject();
        if("zh_TW".equals(language)){
            String s = ChineseHelper.convertToTraditionalChinese(solutionModule.toString());
            newKnowledgeCustomize.fluentPut("solution_module",JSON.parseArray(s));
        }else{
            newKnowledgeCustomize.fluentPut("solution_module",solutionModule);
        }
        kmoResult.fluentPut("knowledge_customize",newKnowledgeCustomize
                .fluentPut("question_basic_info",knowledgeCustomize.get("question_basic_info"))
                .fluentPut("question_detail_info",knowledgeCustomize.get("question_detail_info"))
                .fluentPut("knowledge_title",knowledgeCustomize.get("knowledge_title"))
                .fluentPut("knowledge_description",knowledgeCustomize.get("knowledge_description"))
                .fluentPut("solution_id",knowledgeCustomize.get("solution_id"))
                .fluentPut("solution_name",knowledgeCustomize.get("solution_name"))
                .fluentPut("attachment_info",knowledgeCustomize.get("attachment_info"))
                .fluentPut("question_identify_info",knowledgeCustomize.get("question_identify_info"))
                .fluentPut("knowledge_author",knowledgeCustomize.get("knowledge_author"))
                .fluentPut("question_acceptance_info",knowledgeCustomize.get("question_acceptance_info")));
        return kmoResult;
    }

    /**
     * 组装知识详情数据
     *
     * @param kmoResult
     * @return
     */
    private JSONObject processResponseForDetail(JSONObject kmoResult) {

        //判断不是FRC的详情 CRM是项目，提供项目返回的api文档
        //不是FRC。直接返回/
        JSONArray knowledgeSource = kmoResult.getJSONArray("knowledge_source");
        if (!knowledgeSource.contains("FRC")) {
            return kmoResult;
        }
        JSONObject knowledgeCustomize = kmoResult.getJSONObject("knowledge_customize");
        if (kmoResult.containsKey("create_time")) {
            knowledgeCustomize.put("release_time", kmoResult.getString("create_time"));
        }
        if (kmoResult.containsKey("knowledge_no")) {
            knowledgeCustomize.put("knowledge_no", kmoResult.getString("knowledge_no"));
        }
        if (kmoResult.containsKey("knowledge_name")) {
            knowledgeCustomize.put("knowledge_title", kmoResult.getString("knowledge_name"));
        }
        if (kmoResult.containsKey("knowledge_desc")) {
            knowledgeCustomize.put("knowledge_description", kmoResult.getString("knowledge_desc"));
        }
        if (knowledgeCustomize.containsKey("question_basic_info")) {
            if (knowledgeCustomize.get("question_basic_info") instanceof JSONObject) {
                knowledgeCustomize.put("question_picture", knowledgeCustomize.getJSONObject("question_basic_info").getJSONArray("question_picture"));
            }
            if (knowledgeCustomize.get("question_basic_info") instanceof JSONArray) {
                JSONArray questionPicture = new JSONArray();
                JSONArray questionBasicInfo = knowledgeCustomize.getJSONArray("question_basic_info");
                for (int i = 0; i < questionBasicInfo.size(); i++) {
                    questionPicture.addAll(questionBasicInfo.getJSONObject(i).getJSONArray("question_picture"));
                }
                knowledgeCustomize.put("question_picture", questionPicture);
            }

        }
        // 加入标签信息
        JSONArray tags = kmoResult.getJSONArray("knowledge_tag");
        List<JSONObject> tagList = tags.stream().map(t -> {
            JSONObject kmoTag = (JSONObject) t;
            JSONObject tag = new JSONObject();
            tag.put("tag_id", kmoTag.get("id"));
            tag.put("tag_name", StringUtils.isEmpty(kmoTag.getString("tag_name")) ? kmoTag.getString("tagName") : kmoTag.getString("tag_name"));
            return tag;
        }).collect(Collectors.toList());
        if (knowledgeCustomize.containsKey("attachment_info")) {
            JSONArray attachmentInfo = knowledgeCustomize.getJSONArray("attachment_info");
            JSONArray fileInfo = kmoResult.getJSONArray("file_info");
            for (int i = 0; i < attachmentInfo.size(); i++) {
                JSONObject item = attachmentInfo.getJSONObject(i);
                String attachmentId = item.getString("attachment_id");
                fileInfo.stream().filter(o -> {
                    JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(o));
                    return attachmentId.equals(jsonObject.get("dmc_id"));
                }).forEach(o -> {
                    JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(o));
                    item.put("attachment_download_url", jsonObject.getString("file_download_url"));
                    item.put("attachment_preview_url", jsonObject.getString("file_preview_url"));
                    item.put("attachment_info_id", jsonObject.getString("id"));
                });
            }
        }
        knowledgeCustomize.put("tag_info", tagList);
        knowledgeCustomize.put("knowledge_source", knowledgeSource);
        knowledgeCustomize.put("file_info", kmoResult.getJSONArray("file_info"));
        JSONObject result = new JSONObject();
        result.put("question_result", knowledgeCustomize);
        return result;
    }
}
