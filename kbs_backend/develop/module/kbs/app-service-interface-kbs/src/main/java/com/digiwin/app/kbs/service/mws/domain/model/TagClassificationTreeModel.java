package com.digiwin.app.kbs.service.mws.domain.model;

import com.digiwin.app.kbs.service.mws.domain.TreeNodeVO;
import lombok.Data;

@Data
public class TagClassificationTreeModel extends TreeNodeVO<TagClassificationTreeModel> {
    private String tag_id;
    private String tag_classification_no;
    private String tag_classification_name;

    public TagClassificationTreeModel(Object id, Object parentId, String classificationId, String classificationNo, String classificationName) {
        this.tag_id = classificationId;
        this.tag_classification_no = classificationNo;
        this.tag_classification_name = classificationName;
        this.setId(id);
        this.setParentId(parentId);
    }
}
