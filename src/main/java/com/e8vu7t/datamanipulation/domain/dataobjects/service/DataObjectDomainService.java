package com.e8vu7t.datamanipulation.domain.dataobjects.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.e8vu7t.datamanipulation.domain.dataobjects.model.DataObject;

import lombok.RequiredArgsConstructor;

/**
 * データオブジェクトドメインサービス
 */
@Service
@RequiredArgsConstructor
public class DataObjectDomainService {
    
    /**
     * データオブジェクトリポジトリ
     */
    private final DataObjectRepository dataObjectRepository;
    
    /**
     * データオブジェクトを作成する。
     * @param dataObject データオブジェクト
     * @return 作成したデータオブジェクト
     */
    public DataObject create(DataObject dataObject){
        // 登録済み最大IDを取得
        Optional<Integer> currentMaxId = dataObjectRepository.getMaxId();
        
        var newId = currentMaxId.orElse(0) + 1;
        dataObject.setId(newId);
        dataObjectRepository.insert(dataObject);
        return dataObject;
    }

    /**
     * データオブジェクトを更新する。
     * @param dataObject データオブジェクト
     * @return 更新後のデータオブジェクト
     */
    public DataObject update(DataObject dataObject){
        dataObjectRepository.update(dataObject);
        return dataObject;
    }

    /**
     * データオブジェクト一覧を取得する。
     * @return データオブジェクト一覧
     */
    public List<DataObject> findAll(){
        return dataObjectRepository.findAll();
    }

    /**
     * IDからデータオブジェクトを取得する。
     * @param id ID
     * @return データオブジェクト
     */
    public List<DataObject> findById(int id){
        return dataObjectRepository.findById(id);
    }
}
