package com.e8vu7t.datamanipulation.domain.relations.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.e8vu7t.datamanipulation.domain.relations.model.DataObjectDataPropertyValueRelation;

import lombok.RequiredArgsConstructor;

/**
 * データオブジェクト・データプロパティ・値関係ドメインサービス
 */
@Service
@RequiredArgsConstructor
public class DataObjectDataPropertyValueRelationDomainService {
    
    /**
     * データオブジェクト・データプロパティ・値関係リポジトリ
     */
    private final DataObjectDataPropertyValueRelationRepository objectPropertyValueRelationRepository;
    
    /**
     * データオブジェクト・データプロパティ・値関係を作成する。
     * @param objectPropertyValueRelation データオブジェクト・データプロパティ・値関係
     * @return 作成したデータオブジェクト・データプロパティ・値関係
     */
    public DataObjectDataPropertyValueRelation create(DataObjectDataPropertyValueRelation objectPropertyValueRelation){
        // 登録済み最大IDを取得
        Optional<Integer> currentMaxId = objectPropertyValueRelationRepository.getMaxId();
        
        var newId = currentMaxId.orElse(0) + 1;
        objectPropertyValueRelation.setId(newId);
        objectPropertyValueRelationRepository.insert(objectPropertyValueRelation);
        return objectPropertyValueRelation;
    }

    /**
     * データオブジェクト・データプロパティ・値関係を更新する。
     * @param dataClass データオブジェクト・データプロパティ・値関係
     * @return 更新後のデータオブジェクト・データプロパティ・値関係
     */
    public DataObjectDataPropertyValueRelation update(DataObjectDataPropertyValueRelation dataClass){
        objectPropertyValueRelationRepository.update(dataClass);
        return dataClass;
    }

    /**
     * データオブジェクト・データプロパティ・値関係一覧を取得する。
     * @return データオブジェクト・データプロパティ・値関係一覧
     */
    public List<DataObjectDataPropertyValueRelation> findAll(){
        return objectPropertyValueRelationRepository.findAll();
    }
}
