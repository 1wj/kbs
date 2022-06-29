package com.digiwin.app.kbs.service.mws.domain.model;

import com.digiwin.app.kbs.service.mws.domain.TreeNodeVO;
import lombok.Data;

import java.util.List;

@Data
public class TagTreeModel extends TreeNodeVO<TagTreeModel> {
    private String tag_classification_id;
    private String tag_classification_no;
    private String tag_classification_name;
    private List<TagInfo> tag_info;

    public TagTreeModel(Object id, Object parentId, String classificationId, String tagClassificationNo, String tagClassificationName,List<TagInfo> tagInfoList) {
        this.tag_classification_id = classificationId;
        this.tag_classification_no = tagClassificationNo;
        this.tag_classification_name = tagClassificationName;
        this.tag_info = tagInfoList;
        this.setId(id);
        this.setParentId(parentId);
    }
}
