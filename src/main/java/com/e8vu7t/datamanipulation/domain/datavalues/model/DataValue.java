package com.e8vu7t.datamanipulation.domain.datavalues.model;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * データ値
 */
@Data
public class DataValue {
    
    /**
     * ID
     */
    private int id;

    /**
     * 値
     */
    private String dataContent;

    /**
     * 作成日時
     */
    private LocalDateTime savedDateTime;
}
