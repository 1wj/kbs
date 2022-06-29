package com.digiwin.app.kbs.service.mws.restful;

import com.digiwin.app.container.exceptions.DWException;
import com.digiwin.app.service.AllowAnonymous;
import com.digiwin.app.service.DWService;
import com.digiwin.app.service.DWServiceResult;
import com.digiwin.app.service.restful.DWRequestMapping;
import com.digiwin.app.service.restful.DWRequestMethod;
import com.digiwin.app.service.restful.DWRestfulService;

import java.io.IOException;

/**
 * @ClassName ITestService
 * @Description mws:一期迭代(知识库维护\标签维护\知识检索)
 * @Author HeX
 * @Date 2022/1/18 18:07
 * @Version: v1-知识库迭代一
 **/
@DWRestfulService
public interface ITestService extends DWService {

    /**
     * 新增知识库-测试
     * @return 结果
     * @throws DWException 异常
     */
    @DWRequestMapping(path = "/knowledge/base/test", method = {DWRequestMethod.POST})
    DWServiceResult insertKnowledgeBaseTest(String messageBody) throws DWException, IOException;

}
