package com.digiwin.app.kbs.service.mws.restful;

import com.digiwin.app.service.DWService;
import com.digiwin.app.service.DWServiceResult;
import com.digiwin.app.service.restful.DWRequestMapping;
import com.digiwin.app.service.restful.DWRequestMethod;
import com.digiwin.app.service.restful.DWRestfulService;

/**
 * @ClassName ITagClassificationService
 * @Description mws:一期迭代(标签类别维护)
 * @Author huly
 * @Date 2022/1/20 18:07
 * @Version: v1-知识库迭代一
 **/
@DWRestfulService
public interface ITagClassificationService extends DWService {

    /**
     * 查询标签类别列表
     * @return 结果
     * @throws Exception 异常
     */
    @DWRequestMapping(path = "/tag/classification/info/get", method = {DWRequestMethod.POST})
    DWServiceResult getTagClassificationList(String messageBody) throws Exception;

    /**
     * 标签类别树型结构
     * @return 结果
     * @throws Exception 异常
     */
    @DWRequestMapping(path = "/tag/classification/tree/info/get", method = {DWRequestMethod.POST})
    Object getTagClassificationTree(String messageBody) throws Exception;
    /**
     * 标签树型结构
     * @return 结果
     * @throws Exception 异常
     */
    @DWRequestMapping(path = "/tag/tree/info/get", method = {DWRequestMethod.POST})
    Object getTagTree(String messageBody) throws Exception;

    /**
     * 新增标签类别
     * @return 结果
     * @throws Exception 异常
     */
    @DWRequestMapping(path = "/tag/classification/info/create", method = {DWRequestMethod.POST})
    DWServiceResult insertTagClassification(String messageBody) throws Exception;

    /**
     * 编辑标签类别
     * @return 结果
     * @throws Exception 异常
     */
    @DWRequestMapping(path = "/tag/classification/info/update", method = {DWRequestMethod.POST})
    DWServiceResult updateTagClassification(String messageBody) throws Exception;

    /**
     * 删除标签类别
     * @return 结果
     * @throws Exception 异常
     */
    @DWRequestMapping(path = "/tag/classification/info/delete", method = {DWRequestMethod.POST})
    DWServiceResult deleteTagClassification(String messageBody) throws Exception;

    /**
     * 删除标签类别
     * @return 结果
     * @throws Exception 异常
     */
    @DWRequestMapping(path = "/tag/classification/transfer", method = {DWRequestMethod.POST})
    DWServiceResult transferTagClassification(String messageBody) throws Exception;

    /**
     * 查询标签列表
     * @return 结果
     * @throws Exception 异常
     */
    @DWRequestMapping(path = "/tag/info/get", method = {DWRequestMethod.POST})
    DWServiceResult getTagList(String messageBody) throws Exception;

    /**
     * 新增标签
     * @return 结果
     * @throws Exception 异常
     */
    @DWRequestMapping(path = "/tag/info/create", method = {DWRequestMethod.POST})
    DWServiceResult insertTag(String messageBody) throws Exception;

    /**
     * 更新标签
     * @return 结果
     * @throws Exception 异常
     */
    @DWRequestMapping(path = "/tag/info/update", method = {DWRequestMethod.POST})
    DWServiceResult updateTag(String messageBody) throws Exception;

    /**
     * 标签生效/失效
     * @return 结果
     * @throws Exception 异常
     */
    @DWRequestMapping(path = "/tag/status/update", method = {DWRequestMethod.POST})
    DWServiceResult updateTagStatus(String messageBody) throws Exception;

    /**
     * 删除标签
     * @return 结果
     * @throws Exception 异常
     */
    @DWRequestMapping(path = "/tag/info/delete", method = {DWRequestMethod.POST})
    DWServiceResult deleteTag(String messageBody) throws Exception;

    /**
     * 標籤複製轉移
     * @return 结果
     * @throws Exception 异常
     */
    @DWRequestMapping(path = "/tag/copy/transfer", method = {DWRequestMethod.POST})
    DWServiceResult copyTransferTag(String messageBody) throws Exception;




}
