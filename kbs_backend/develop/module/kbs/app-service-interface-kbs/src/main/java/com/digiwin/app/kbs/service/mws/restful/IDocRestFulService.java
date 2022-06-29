package com.digiwin.app.kbs.service.mws.restful;

import com.digiwin.app.service.DWService;
import com.digiwin.app.service.restful.DWRequestMapping;
import com.digiwin.app.service.restful.DWRequestMethod;
import com.digiwin.app.service.restful.DWRestfulService;

/**
 * @Date 2022/1/20 11:01
 * @Created yanggld
 * @Description
 */
@DWRestfulService
public interface IDocRestFulService extends DWService {

    /**
     * 新增知识库
     *
     * @param messageBody
     * @return
     * @throws Exception
     */
    @DWRequestMapping(path = "knowledge/base/info/create", method = {DWRequestMethod.POST})
    Object createKnowledgeBaseInfo(String messageBody) throws Exception;

    /**
     * 更新知识库
     *
     * @param messageBody
     * @return
     * @throws Exception
     */
    @DWRequestMapping(path = "knowledge/base/info/update", method = {DWRequestMethod.POST})
    Object updateKnowledgeBaseInfo(String messageBody) throws Exception;

    /**
     * 删除知识库
     *
     * @param messageBody
     * @return
     * @throws Exception
     */
    @DWRequestMapping(path = "knowledge/base/info/delete", method = {DWRequestMethod.POST})
    Object deleteKnowledgeBaseInfo(String messageBody) throws Exception;

    /**
     * 知识库名称校验
     *
     * @param messageBody
     * @return
     * @throws Exception
     */
    @DWRequestMapping(path = "knowledge/base/name/verify", method = {DWRequestMethod.POST})
    Object verifyKnowledgeBaseName(String messageBody) throws Exception;

    /**
     * 获取知识库
     *
     * @param messageBody
     * @return
     * @throws Exception
     */
    @DWRequestMapping(path = "knowledge/base/info/get", method = {DWRequestMethod.POST})
    Object getKnowledgeBaseInfo(String messageBody) throws Exception;

    /**
     * 获取知识库树状结构
     *
     * @param messageBody
     * @return
     * @throws Exception
     */
    @DWRequestMapping(path = "knowledge/base/tree/info/get", method = {DWRequestMethod.POST})
    Object getKnowledgeBaseTreeInfo(String messageBody) throws Exception;

    /**
     * 新增知识类别
     *
     * @param messageBody
     * @return
     * @throws Exception
     */
    @DWRequestMapping(path = "knowledge/classification/info/create", method = {DWRequestMethod.POST})
    Object createKnowledgeClassificationInfo(String messageBody) throws Exception;

    /**
     * 更新知识类别
     *
     * @param messageBody
     * @return
     * @throws Exception
     */
    @DWRequestMapping(path = "knowledge/classification/info/update", method = {DWRequestMethod.POST})
    Object updateKnowledgeClassificationInfo(String messageBody) throws Exception;

    /**
     * 删除知识类别
     *
     * @param messageBody
     * @return
     * @throws Exception
     */
    @DWRequestMapping(path = "knowledge/classification/info/delete", method = {DWRequestMethod.POST})
    Object deleteKnowledgeClassificationInfo(String messageBody) throws Exception;

    /**
     * 知识类别转移
     *
     * @param messageBody
     * @return
     * @throws Exception
     */
    @DWRequestMapping(path = "knowledge/classification/transfer", method = {DWRequestMethod.POST})
    Object transferKnowledgeClassification(String messageBody) throws Exception;









}
