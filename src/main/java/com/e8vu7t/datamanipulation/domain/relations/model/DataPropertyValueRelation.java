package com.e8vu7t.datamanipulation.domain.relations.model;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * データプロパティ・値関係
 */
@Data
public class DataPropertyValueRelation {
    
    /**
     * ID
     */
    private int id;

    /**
     * データプロパティID
     */
    private int datapropertyId;

    /**
     * 値ID
     */
    private int datavalueId;

    /**
     * 作成日時
     */
    private LocalDateTime savedDateTime;
}
