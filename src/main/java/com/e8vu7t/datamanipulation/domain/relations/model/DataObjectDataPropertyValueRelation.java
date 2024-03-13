package com.e8vu7t.datamanipulation.domain.relations.model;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * データオブジェクト・データプロパティ・値関係
 */
@Data
public class DataObjectDataPropertyValueRelation {
    
    /**
     * ID
     */
    private int id;

    /**
     * データオブジェクトID
     */
    private int dataobjectId;

    /**
     * データプロパティ・値関係ID
     */
    private int datapropertyValueRelationId;

    /**
     * 作成日時
     */
    private LocalDateTime savedDateTime;
}
