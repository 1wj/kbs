package com.digiwin.app.kbs.service.mws.biz;

import com.alibaba.fastjson.JSONObject;
import com.digiwin.app.kbs.service.mws.domain.model.toKmo.KnoHomeModel;
import com.digiwin.app.kbs.service.mws.domain.model.toKmo.KnoInfoGetModel;
import com.digiwin.app.kbs.service.mws.domain.model.toKmo.KnoModel;

/**
 * @ClassName ToKmobiz
 * @Description 与kmo交互逻辑
 * @Version 1.1
 **/
public interface ToKmoBiz {

    /**
     * 知识库首页检索
     *
     * @param knoHomeModel 知识库首页检索入参
     * @return 返回json
     */
    JSONObject getKnowledgeBaseHome(KnoHomeModel knoHomeModel) throws Exception;

    /**
     * 获取知识清单
     *
     * @param knoInfoGetModel 获取知识清单入参
     * @return 返回json
     */
    JSONObject getKnowledgeInfo(KnoInfoGetModel knoInfoGetModel) throws Exception;

    /**
     * 获取知识详情
     *
     * @param oid 知识model
     * @return 查询出的详情信息
     */
    JSONObject knowledgeDetail(String oid) throws Exception;

    /**
     * 知识复制转移
     *
     * @param param
     * @return
     */
    JSONObject transferKnowledgeInfoCopy(String param) throws Exception;

    /**
     * 知识删除
     *
     * @param param
     * @return
     */
    JSONObject deleteKnowledgeInfoDelete(String param) throws Exception;

    /**
     * 更新知识
     *
     * @param param
     * @return
     */
    JSONObject updateKnowledgeInfo(String param) throws Exception;

    /**
     * 获取文档详情
     *
     * @param param
     * @return
     */
    JSONObject getFileDetail(String param) throws Exception;

    /**
     * 获取搜索提示
     *
     * @param messageBody
     * @return
     */
    JSONObject searchTips(String messageBody) throws Exception;

    /**
     * 获取知识中台地端url
     *
     * @param messageBody
     * @return
     */
    JSONObject getKmoLocalUrl(String messageBody) throws Exception;

}
