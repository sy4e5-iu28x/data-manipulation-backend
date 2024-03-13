package com.e8vu7t.datamanipulation.domain.relations.service;

import java.util.List;
import java.util.Optional;

import com.e8vu7t.datamanipulation.domain.relations.model.DataObjectDataPropertyValueRelation;

/**
 * データオブジェクト・データプロパティ・値関係リポジトリ
 */
public interface DataObjectDataPropertyValueRelationRepository {
    /**
     * データオブジェクト・データプロパティ・値関係を追加する。
     * @param objectPropertyValueRelation 追加後のデータオブジェクト・データプロパティ・値関係
     */
    void insert(DataObjectDataPropertyValueRelation objectPropertyValueRelation);

    /**
     * データオブジェクト・データプロパティ・値関係の一覧を取得する。
     * @return データオブジェクト・データプロパティ・値関係一覧
     */
    List<DataObjectDataPropertyValueRelation> findAll();

    /**
     * データオブジェクト・データプロパティ・値関係の最大IDを取得する。
     * @return データオブジェクト・データプロパティ・値関係IDでの最大ID
     */
    Optional<Integer> getMaxId();

    /**
     * データオブジェクト・データプロパティ・値関係を更新する。
     * @param objectPropertyValueRelation データオブジェクト・データプロパティ・値関係
     * @return 更新後のデータオブジェクト・データプロパティ・値関係
     */
    int update(DataObjectDataPropertyValueRelation objectPropertyValueRelation);
}
