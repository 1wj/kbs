package com.digiwin.app.kbs.service.mws.restful;

import com.digiwin.app.service.DWService;
import com.digiwin.app.service.DWServiceResult;
import com.digiwin.app.service.restful.DWRequestMapping;
import com.digiwin.app.service.restful.DWRequestMethod;
import com.digiwin.app.service.restful.DWRestfulService;

/**
 * @ClassName IToKmoService
 * @Description 与kmo交互
 * @Version 1.1
 **/
@DWRestfulService
public interface IToKmoService extends DWService {

    /**
     * 知识库首页檢索
     *
     * @param messageBody 查询入参
     * @return
     * @throws Exception
     */
    @DWRequestMapping(path = "/knowledge/base/home/info/get", method = {DWRequestMethod.POST})
    DWServiceResult getKnowledgeBaseHome(String messageBody) throws Exception;

    /**
     * 获取知识清单
     *
     * @param messageBody
     * @return
     * @throws Exception
     */
    @DWRequestMapping(path = "/knowledge/info/get", method = {DWRequestMethod.POST})
    DWServiceResult getKnowledgeInfo(String messageBody) throws Exception;

    /**
     * 获取知识详情
     * @return 结果
     * @throws Exception 异常
     */
    @DWRequestMapping(path = "/knowledge/detail/info/get", method = {DWRequestMethod.POST})
    DWServiceResult knowledgeDetail(String messageBody) throws Exception;

    /**
     * 知识复制转移
     *
     * @param messageBody
     * @return
     * @throws Exception
     */
    @DWRequestMapping(path = "/knowledge/info/copy/transfer", method = {DWRequestMethod.POST})
    DWServiceResult transferKnowledgeInfoCopy(String messageBody) throws Exception;

    /**
     * 删除知识
     *
     * @param messageBody
     * @return
     * @throws Exception
     */
    @DWRequestMapping(path = "/knowledge/info/delete", method = {DWRequestMethod.POST})
    DWServiceResult deleteKnowledgeInfoDelete(String messageBody) throws Exception;

    /**
     * 标签调整
     *
     * @param messageBody
     * @return
     * @throws Exception
     */
    @DWRequestMapping(path = "/knowledge/tag/update", method = {DWRequestMethod.POST})
    DWServiceResult updateKnowledgeInfo(String messageBody) throws Exception;


    /**
     * 根据文档主键查询文档详情
     * @param messageBody
     * @return
     * @throws Exception
     */
    @DWRequestMapping(path = "/knowledge/file/detail/get", method = {DWRequestMethod.POST})
    DWServiceResult getFileDetail(String messageBody) throws Exception;
    /**
     * 获取搜索提示
     *
     * @param messageBody
     * @return
     * @throws Exception
     */
    @DWRequestMapping(path = "/knowledge/search/tips/get", method = {DWRequestMethod.POST})
    DWServiceResult searchTips(String messageBody) throws Exception;
    /**
     * 获取知识中台地端url
     *
     * @param messageBody
     * @return
     * @throws Exception
     */
    @DWRequestMapping(path = "/kmo/local/url/get", method = {DWRequestMethod.GET})
    DWServiceResult getKmoLocalUrl(String messageBody) throws Exception;
}
