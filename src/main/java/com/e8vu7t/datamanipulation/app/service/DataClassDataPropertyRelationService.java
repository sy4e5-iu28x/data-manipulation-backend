package com.e8vu7t.datamanipulation.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e8vu7t.datamanipulation.domain.relations.model.DataClassDataPropertyRelation;
import com.e8vu7t.datamanipulation.domain.relations.service.DataClassDataPropertyRelationDomainService;

import lombok.RequiredArgsConstructor;

/**
 * 関係サービス
 */
@RequiredArgsConstructor
@Service
@Transactional
public class DataClassDataPropertyRelationService {
    /**
     * データクラス・データプロパティ関係ドメインサービス
     */
    private final DataClassDataPropertyRelationDomainService classPropertyRelationDomainService;

    /**
     * データクラス・データプロパティ関係を作成する。
     * @param dataProperty データクラス・データプロパティ関係
     * @return 作成したデータクラス・データプロパティ関係
     */
    public DataClassDataPropertyRelation create(DataClassDataPropertyRelation dataProperty){
        return classPropertyRelationDomainService.create(dataProperty);
    }

    /**
     * データクラス・データプロパティ関係を更新する。
     * @param dataProperty データクラス・データプロパティ関係
     * @return 更新後のデータクラス・データプロパティ関係
     */
    public DataClassDataPropertyRelation update(DataClassDataPropertyRelation dataProperty){
        return classPropertyRelationDomainService.update(dataProperty);
    }

    /**
     * データクラス・データプロパティ関係の一覧を取得する。
     * @return データクラス・データプロパティ関係の一覧
     */
    public List<DataClassDataPropertyRelation> findAll() {
        return classPropertyRelationDomainService.findAll();
    }

    /**
     * データクラスIDをキーに、データクラス・データプロパティ関係の一覧を取得する。
     * @param dataClassId データクラスID
     * @return データクラス・データプロパティ関係の一覧
     */
    public List<DataClassDataPropertyRelation> findByDataClassId(int dataClassId) {
        return classPropertyRelationDomainService.findByDataClassId(dataClassId);
    }
}
