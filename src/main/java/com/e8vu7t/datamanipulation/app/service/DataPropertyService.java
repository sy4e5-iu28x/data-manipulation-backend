package com.e8vu7t.datamanipulation.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e8vu7t.datamanipulation.domain.dataproperties.model.DataProperty;
import com.e8vu7t.datamanipulation.domain.dataproperties.service.DataPropertyDomainService;

import lombok.RequiredArgsConstructor;

/**
 * データプロパティサービス
 */
@RequiredArgsConstructor
@Service
@Transactional
public class DataPropertyService {
    
    /**
     * データプロパティドメインサービス
     */
    private final DataPropertyDomainService dataPropertyDomainService;

    /**
     * データプロパティを作成する。
     * @param dataProperty データプロパティ
     * @return 作成したデータプロパティ
     */
    public DataProperty create(DataProperty dataProperty){
        return dataPropertyDomainService.create(dataProperty);
    }

    /**
     * データプロパティを更新する。
     * @param dataProperty データプロパティ
     * @return 更新後のデータプロパティ
     */
    public DataProperty update(DataProperty dataProperty){
        return dataPropertyDomainService.update(dataProperty);
    }

    /**
     * データプロパティの一覧を取得する。
     * @return データプロパティの一覧
     */
    public List<DataProperty> findAll() {
        return dataPropertyDomainService.findAll();
    }
}
