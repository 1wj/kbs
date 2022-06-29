package com.digiwin.app.kbs.service.configuration.aspect;

import cn.hutool.core.date.DateUtil;
import com.digiwin.app.kbs.service.common.domain.entity.BaseEntity;
import com.digiwin.app.kbs.service.util.TenantTokenUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Objects;


/**
 * @Description: mongoDB切面-基础类对应操作
 * @Version: v1-知识库迭代一
 */

@Aspect
@Component
public class MongoCurdFieldsAspect {

    /**
     * MongoDB插入修改操作
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("execution(public * org.springframework.data.mongodb.core.MongoTemplate.insert(..))" +
            "|| execution(public * org.springframework.data.mongodb.core.MongoTemplate.save(..)) " +
            "|| execution(public * org.springframework.data.mongodb.core.MongoOperations.insertAll(..)) ")
    public Object beforeMongoDB(ProceedingJoinPoint pjp) throws Throwable {
        setManagerFields(pjp.getArgs());
        return pjp.proceed();
    }

    /**
     * 设置基础字段值
     *
     * @param args 参数
     */
    private void setManagerFields(Object[] args) {
        if (args.length == 0) {
            return;
        }
        if (args[0] instanceof Collection) {
            Collection objects = (Collection) args[0];
            for (Object o : objects) {
                setManagerFieldsValue(o);
            }
        } else {
            setManagerFieldsValue(args[0]);
        }
    }

    private void setManagerFieldsValue(Object arg) {
        if (Objects.isNull(arg)) {
            return;
        }
        //判断是否是带token来的，没有租户的话，return
        if (!TenantTokenUtil.isTenant()) {
            return;
        }
        //继承baseDoc基础类
        BaseEntity baseDoc = (BaseEntity) arg;
        if (StringUtils.isEmpty(baseDoc.getObjectId())) {
            setValue(baseDoc);
            if (StringUtils.isEmpty(baseDoc.getDocVersion())) {
                baseDoc.setDocVersion(1);
            }
        } else {
            setValue(baseDoc);
            if (StringUtils.isEmpty(baseDoc.getDocVersion())) {
                baseDoc.setDocVersion(1);
            } else {
                baseDoc.setDocVersion(baseDoc.getDocVersion() + 1);
            }
            if (StringUtils.isEmpty(baseDoc.getUpdateName())) {
                baseDoc.setUpdateName(TenantTokenUtil.getUserName());
            }
            baseDoc.setUpdateTime(DateUtil.date());
        }
    }

    private void setValue(BaseEntity baseDoc) {
        if (StringUtils.isEmpty(baseDoc.getTenantsid())) {
            baseDoc.setTenantsid(TenantTokenUtil.getTenantSid());
        }
        if (StringUtils.isEmpty(baseDoc.getCreateName())) {
            baseDoc.setCreateName(TenantTokenUtil.getUserName());
        }
        if (StringUtils.isEmpty(baseDoc.getCreateTime())) {
            baseDoc.setCreateTime(DateUtil.date());
        }
        if (StringUtils.isEmpty(baseDoc.getDocState())) {
            baseDoc.setDocState(Boolean.TRUE);
        }
    }
}
