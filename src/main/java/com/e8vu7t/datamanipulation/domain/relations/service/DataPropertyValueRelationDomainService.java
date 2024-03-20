package com.e8vu7t.datamanipulation.domain.relations.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.e8vu7t.datamanipulation.domain.relations.model.DataPropertyValueRelation;

import lombok.RequiredArgsConstructor;

/**
 * データプロパティ・値関係ドメインサービス
 */
@Service
@RequiredArgsConstructor
public class DataPropertyValueRelationDomainService {
    
    /**
     * データプロパティ・値関係リポジトリ
     */
    private final DataPropertyValueRelationRepository propertyValueRelationRepository;
    
    /**
     * データプロパティ・値関係を作成する。
     * @param propertyValueRelation データプロパティ・値関係
     * @return 作成したデータプロパティ・値関係
     */
    public DataPropertyValueRelation create(DataPropertyValueRelation propertyValueRelation){
        // 登録済み最大IDを取得
        Optional<Integer> currentMaxId = propertyValueRelationRepository.getMaxId();
        
        var newId = currentMaxId.orElse(0) + 1;
        propertyValueRelation.setId(newId);
        propertyValueRelationRepository.insert(propertyValueRelation);
        return propertyValueRelation;
    }

    /**
     * データプロパティ・値関係を更新する。
     * @param propertyValueRelation データプロパティ・値関係
     * @return 更新後のデータプロパティ・値関係
     */
    public DataPropertyValueRelation update(DataPropertyValueRelation propertyValueRelation){
        propertyValueRelationRepository.update(propertyValueRelation);
        return propertyValueRelation;
    }

    /**
     * データプロパティ・値関係一覧を取得する。
     * @return データプロパティ・値関係一覧
     */
    public List<DataPropertyValueRelation> findAll(){
        return propertyValueRelationRepository.findAll();
    }

    /**
     * データプロパティ・値関係をプロパティID、値IDから取得する。
     * @param param データプロパティ・値関係
     * @return データプロパティ・値関係
     */
    public List<DataPropertyValueRelation> findByPropertyValue(DataPropertyValueRelation param){
        return propertyValueRelationRepository.findByPropertyValue(param);
    }

    /**
     * IDからデータプロパティ・値関係を取得する。
     * @param id ID
     * @return データプロパティ・値関係
     */
    public List<DataPropertyValueRelation> findById(int id){
        return propertyValueRelationRepository.findById(id);
    }
}
