package com.e8vu7t.datamanipulation.domain.dataclasses.service;

import java.util.List;
import java.util.Optional;

import com.e8vu7t.datamanipulation.domain.dataclasses.model.DataClass;

/**
 * データクラスリポジトリ
 */
public interface DataClassRepository {
    /**
     * データクラスを追加する。
     * @param dataClass 追加後のデータクラス
     */
    void insert(DataClass dataClass);

    /**
     * データクラスの一覧を取得する。
     * @return データクラス一覧
     */
    List<DataClass> findAll();

    /**
     * データクラスの最大IDを取得する。
     * @return データクラスIDでの最大ID
     */
    Optional<Integer> getMaxId();

    /**
     * データクラスを更新する。
     * @param dataClass
     * @return 更新後のデータクラス
     */
    int update(DataClass dataClass);
}
