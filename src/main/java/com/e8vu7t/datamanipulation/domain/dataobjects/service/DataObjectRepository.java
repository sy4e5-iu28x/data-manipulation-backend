package com.e8vu7t.datamanipulation.domain.dataobjects.service;

import java.util.List;
import java.util.Optional;

import com.e8vu7t.datamanipulation.domain.dataobjects.model.DataObject;

/**
 * データオブジェクトリポジトリ
 */
public interface DataObjectRepository {
    /**
     * データオブジェクトを追加する。
     * @param dataObject 追加後のデータオブジェクト
     */
    void insert(DataObject dataObject);

    /**
     * データオブジェクトの一覧を取得する。
     * @return データオブジェクト一覧
     */
    List<DataObject> findAll();

    /**
     * データオブジェクトの最大IDを取得する。
     * @return データオブジェクトIDでの最大ID
     */
    Optional<Integer> getMaxId();

    /**
     * データオブジェクトを更新する。
     * @param dataObject データオブジェクト
     * @return 更新後のデータオブジェクト
     */
    int update(DataObject dataObject);
}
