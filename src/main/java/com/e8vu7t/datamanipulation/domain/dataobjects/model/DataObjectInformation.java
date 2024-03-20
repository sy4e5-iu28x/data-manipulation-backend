package com.e8vu7t.datamanipulation.domain.dataobjects.model;

import java.util.List;

import com.e8vu7t.datamanipulation.domain.dataproperties.model.DataProperty;
import com.e8vu7t.datamanipulation.domain.datavalues.model.DataValue;

import lombok.Data;

/**
 * データオブジェクト情報<br>リクエストやレスポンス内容を保持する
 */
@Data
public class DataObjectInformation {
    /**
     * データオブジェクトID
     */
    private int dataobjectId;

    /**
     * データクラスID
     */
    private int dataclassId;

    /**
     * データプロパティ定義
     */
    private List<DataProperty> dataproperties;

    /**
     * 値定義
     */
    private List<DataValue> datavalues;
}
