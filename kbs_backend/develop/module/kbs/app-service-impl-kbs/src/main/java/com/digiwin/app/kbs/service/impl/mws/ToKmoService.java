package com.digiwin.app.kbs.service.impl.mws;

import com.digiwin.app.kbs.service.configuration.annotation.ParamValidationHandler;
import com.digiwin.app.kbs.service.mongo.document.KnowledgeBaseDoc;
import com.digiwin.app.kbs.service.mws.biz.ToKmoBiz;
import com.digiwin.app.kbs.service.mws.domain.model.KnoBaseModel;
import com.digiwin.app.kbs.service.mws.domain.model.toKmo.KnoFileModel;
import com.digiwin.app.kbs.service.mws.domain.model.toKmo.KnoHomeModel;
import com.digiwin.app.kbs.service.mws.domain.model.toKmo.KnoInfoGetModel;
import com.digiwin.app.kbs.service.mws.domain.model.toKmo.KnoModel;
import com.digiwin.app.kbs.service.mws.restful.IToKmoService;
import com.digiwin.app.kbs.service.util.BizUtil;
import com.digiwin.app.kbs.service.util.ResultTool;
import com.digiwin.app.service.DWServiceResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName ToKmoService
 * @Description 与KMO交互
 * @Version 1.1
 **/
public class ToKmoService implements IToKmoService {

    @Autowired
    ToKmoBiz toKmoBiz;

    @Override
    public DWServiceResult getKnowledgeBaseHome(String messageBody) {

        DWServiceResult result = new DWServiceResult();
        try {
            // 格式转换 string -> model
            KnoHomeModel knoHomeModel = BizUtil.str2TByKey(messageBody, "knowledge_info", KnoHomeModel.class);
            // 必要参数校验
            ParamValidationHandler.validateParams(knoHomeModel);
            ParamValidationHandler.validateParams(knoHomeModel.getPagination());
            result.setData(toKmoBiz.getKnowledgeBaseHome(knoHomeModel));
            result.setSuccess(true);
            result.setMessage("success!");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(ExceptionUtils.getMessage(e));
        }
        return result;
    }

    @Override
    public DWServiceResult getKnowledgeInfo(String messageBody) {
        DWServiceResult result = new DWServiceResult();
        try {
            // 解析kbs入参
            KnoInfoGetModel knoInfoGetModel = BizUtil.str2TByKey(messageBody, "knowledge_info", KnoInfoGetModel.class);
            ParamValidationHandler.validateParams(knoInfoGetModel);
            result.setData(toKmoBiz.getKnowledgeInfo(knoInfoGetModel));
            result.setSuccess(true);
            result.setMessage("success!");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(ExceptionUtils.getMessage(e));
        }
        return result;
    }

    @Override
    public DWServiceResult knowledgeDetail(String messageBody) {
        DWServiceResult result = new DWServiceResult();
        try {
            // 解析kbs 入参
            KnoModel knoModel = BizUtil.str2TByKey(messageBody, "knowledge_info", KnoModel.class);
            ParamValidationHandler.validateParams(knoModel);
            result.setData(toKmoBiz.knowledgeDetail(messageBody));
            result.setSuccess(true);
            result.setMessage("success!");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(ExceptionUtils.getMessage(e));
        }
        return result;
    }

    @Override
    public DWServiceResult transferKnowledgeInfoCopy(String messageBody) {
        DWServiceResult result = new DWServiceResult();
        try {

            // 解析kbs 入参
            List<KnoModel> knoModels = BizUtil.getKnowledgeReqBizParam(messageBody, "knowledge_info", KnoModel.class);
            for (KnoModel knoModel : knoModels) {
                if (StringUtils.isEmpty(knoModel.getKnowledgeId())) {
                    return ResultTool.fail("knowledge_id is null");
                }
            }
            result.setData(toKmoBiz.transferKnowledgeInfoCopy(messageBody));
            result.setSuccess(true);
            result.setMessage("success!");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(ExceptionUtils.getMessage(e));
        }
        return result;
    }

    @Override
    public DWServiceResult deleteKnowledgeInfoDelete(String messageBody) {
        DWServiceResult result = new DWServiceResult();
        try {
            List<KnoModel> knoModel = BizUtil.getKnowledgeReqBizParam(messageBody, "knowledge_info", KnoModel.class);
            for (KnoModel kno : knoModel) {
                if (StringUtils.isEmpty(kno.getKnowledgeId())) {
                    return ResultTool.fail("knowledge_id is null");
                }
            }
            result.setData(toKmoBiz.deleteKnowledgeInfoDelete(messageBody));
            result.setSuccess(true);
            result.setMessage("success!");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(ExceptionUtils.getMessage(e));
        }
        return result;
    }

    @Override
    public DWServiceResult updateKnowledgeInfo(String messageBody) {
        DWServiceResult result = new DWServiceResult();
        try {

            result.setData(toKmoBiz.updateKnowledgeInfo(messageBody));
            result.setSuccess(true);
            result.setMessage("success!");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(ExceptionUtils.getMessage(e));
        }
        return result;
    }

    @Override
    public DWServiceResult getFileDetail(String messageBody) {
        DWServiceResult result = new DWServiceResult();
        try {

            KnoFileModel knoFileModel = BizUtil.str2TByKey(messageBody, "file_info", KnoFileModel.class);
            if (StringUtils.isEmpty(knoFileModel.getFileId())) {
                return ResultTool.fail("file_id is null");
            }
            result.setData(toKmoBiz.getFileDetail(messageBody));
            result.setSuccess(true);
            result.setMessage("success!");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(ExceptionUtils.getMessage(e));
        }
        return result;
    }

    /**
     * 获取搜索提示
     *
     * @param messageBody
     * @return
     * @throws Exception
     */
    @Override
    public DWServiceResult searchTips(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {

            result.setData(toKmoBiz.searchTips(messageBody));
            result.setSuccess(true);
            result.setMessage("success!");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(ExceptionUtils.getMessage(e));
        }
        return result;
    }

    /**
     * 获取知识中台地端url
     *
     * @param messageBody
     * @return
     * @throws Exception
     */
    @Override
    public DWServiceResult getKmoLocalUrl(String messageBody) throws Exception {
        DWServiceResult result = new DWServiceResult();
        try {
            result.setData(toKmoBiz.getKmoLocalUrl(messageBody));
            result.setSuccess(true);
            result.setMessage("success!");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(ExceptionUtils.getMessage(e));
        }
        return result;
    }
}
