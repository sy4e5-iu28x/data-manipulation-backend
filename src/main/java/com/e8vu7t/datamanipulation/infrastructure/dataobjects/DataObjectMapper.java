package com.e8vu7t.datamanipulation.infrastructure.dataobjects;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.e8vu7t.datamanipulation.domain.dataobjects.model.DataObject;

/**
 * データオブジェクトマッパー
 */
@Mapper
public interface DataObjectMapper {
    /**
     * データオブジェクトを追加する。
     * @param dataObject データオブジェクト
     */
    void insert(DataObject dataObject);

    /**
     * データオブジェクトの一覧を取得する。
     * @return データオブジェクト一覧
     */
    List<DataObject> findAll();

    /**
     * IDからデータオブジェクトを取得する。
     * @param id ID
     * @return データオブジェクト
     */
    List<DataObject> findById(int id);

    /**
     * データオブジェクトの最大IDを取得する。
     * @return データオブジェクトIDでの最大ID
     */
    Optional<Integer> getMaxId();

    /**
     * データオブジェクトを更新する。
     * @param dataObject
     * @return 更新後のデータオブジェクト
     */
    int update(DataObject dataObject);
}
