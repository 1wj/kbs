package com.digiwin.app.kbs.service.impl.mws;

import com.alibaba.fastjson.JSONObject;
import com.digiwin.app.container.exceptions.DWException;
import com.digiwin.app.kbs.service.mongo.document.KnowledgeBaseDoc;
import com.digiwin.app.kbs.service.mongo.document.KnowledgeClassificationDoc;
import com.digiwin.app.kbs.service.mws.biz.KnowledgeBaseBiz;
import com.digiwin.app.kbs.service.mws.domain.model.KnoBaseModel;
import com.digiwin.app.kbs.service.mws.domain.model.KnoClassificationModel;
import com.digiwin.app.kbs.service.mws.restful.ITestService;
import com.digiwin.app.service.DWServiceResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName TestService
 * @Description 测试类
 * @Author HeX
 * @Date 2022/1/18 18:11
 * @Version 1.0
 **/
public class TestService implements ITestService {

    @Autowired
    KnowledgeBaseBiz knowledgeBaseBiz;

    @Override
    public DWServiceResult insertKnowledgeBaseTest(String messageBody) throws DWException, IOException {
        DWServiceResult result = new DWServiceResult();
        try {
            // 接收入参，转model
            KnoBaseModel knoBaseModel = new ObjectMapper().readValue(messageBody.getBytes(), KnoBaseModel.class);
            // model转entity
            KnowledgeBaseDoc knowledgeBaseDoc = new KnowledgeBaseDoc();
            BeanUtils.copyProperties(knoBaseModel,knowledgeBaseDoc);
            KnowledgeBaseDoc doc = knowledgeBaseBiz.addKnowledgeClassification(knowledgeBaseDoc);
            result.setData(doc.toString());
            result.setSuccess(true);
            result.setMessage("success!");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

}
