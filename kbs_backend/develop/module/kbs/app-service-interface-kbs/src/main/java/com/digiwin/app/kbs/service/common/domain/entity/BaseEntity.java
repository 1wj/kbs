package com.digiwin.app.kbs.service.common.domain.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.Date;

/**
 * @Description: 基础实体类
 * @Version: v1-知识库迭代一
 */
@Data
public class BaseEntity{
    @Id
    @Field("_id")
    private ObjectId objectId;
    /**
     * 租户
     */
    @Field("tenantsid")
    private Long tenantsid;
    /**
     * 租户
     */
    @Field("tenantId")
    private String tenantId;
    /**
     * 创建时间
     */
    @Field("create_time")
    private Date createTime;
    /**
     * 创建人
     */
    @Field("create_name")
    private String createName;
    /**
     * 修改时间
     */
    @Field("update_time")
    private Date updateTime;
    /**
     * 修改人
     */
    @Field("update_name")
    private String updateName;
    /**
     * 文档状态
     */
    @Field("doc_state")
    private Boolean docState;
    /**
     * 文档版本
     */
    @Field("doc_version")
    private Integer docVersion;
    /**
     * 文档备注
     */
    @Field("doc_remarks")
    private String docRemarks;
}
