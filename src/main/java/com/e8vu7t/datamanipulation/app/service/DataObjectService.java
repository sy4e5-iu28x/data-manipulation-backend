package com.e8vu7t.datamanipulation.app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e8vu7t.datamanipulation.domain.dataobjects.model.DataObject;
import com.e8vu7t.datamanipulation.domain.dataobjects.model.DataObjectInformation;
import com.e8vu7t.datamanipulation.domain.dataobjects.service.DataObjectDomainService;
import com.e8vu7t.datamanipulation.domain.dataproperties.model.DataProperty;
import com.e8vu7t.datamanipulation.domain.dataproperties.service.DataPropertyDomainService;
import com.e8vu7t.datamanipulation.domain.datavalues.model.DataValue;
import com.e8vu7t.datamanipulation.domain.datavalues.service.DataValueDomainService;
import com.e8vu7t.datamanipulation.domain.relations.model.DataObjectDataPropertyValueRelation;
import com.e8vu7t.datamanipulation.domain.relations.model.DataPropertyValueRelation;
import com.e8vu7t.datamanipulation.domain.relations.service.DataObjectDataPropertyValueRelationDomainService;
import com.e8vu7t.datamanipulation.domain.relations.service.DataPropertyValueRelationDomainService;

import lombok.RequiredArgsConstructor;

/**
 * データオブジェクトサービス
 */
@RequiredArgsConstructor
@Service
@Transactional
public class DataObjectService {
    
    /**
     * データプロパティ・値関係ドメインサービス
     */
    private final DataPropertyValueRelationDomainService propertyValueRelationDomainService;

    /**
     * データ値ドメインサービス
     */
    private final DataValueDomainService dataValueDomainService;

    /**
     * データオブジェクト・データプロパティ・値関係ドメインサービス
     */
    private final DataObjectDataPropertyValueRelationDomainService objectPropertyValueRelationDomainService;

    /**
     * データオブジェクトドメインサービス
     */
    private final DataObjectDomainService dataobjectDomainService;

    /**
     * データプロパティドメインサービス
     */
    private final DataPropertyDomainService datapropertyDomainService;

    /**
     * データオブジェクトとプロパティ、値と各関係を作成する。
     * @param dataObjectRequest データオブジェクトリクエスト
     * @return 作成したデータオブジェクト情報
     */
    public DataObjectInformation create(DataObjectInformation dataObjectRequest){
        // データオブジェクト作成
        final DataObject dataObject = new DataObject();
        dataObject.setDataclassId(dataObjectRequest.getDataclassId());

        // 登録後のデータオブジェクト(IDを後続で使用する)
        final DataObject createdDataObject = dataobjectDomainService.create(dataObject);

        // 登録後のデータ値 (IDを後続で使用する)
        final List<DataValue> createdValues = dataObjectRequest.getDatavalues().stream()
        .map(valueRecord -> {
            // 保存日時
            valueRecord.setSavedDateTime(LocalDateTime.now());
            return dataValueDomainService.create(valueRecord);
        }).toList();

        // 対象のデータプロパティ
        final List<DataProperty> targetProperties = dataObjectRequest.getDataproperties().stream()
        .map(propertyRecord -> {
            return datapropertyDomainService.findById(propertyRecord.getId())
                .stream().findFirst().get();
        }).toList();
        
        // データ値イテレータ
        Iterator<DataValue> dataValueIterator = createdValues.iterator();
        // データプロパティイテレータ
        Iterator<DataProperty> dataPropertyIterator = targetProperties.iterator();

        // 作成後データプロパティ・値関係保持用
        final List<DataPropertyValueRelation> createdPropertyValueRelations = new ArrayList<>();
        while(dataValueIterator.hasNext() && dataPropertyIterator.hasNext()){
            // データプロパティ
            DataProperty currentProperty = dataPropertyIterator.next();
            // データ値
            DataValue currentValue = dataValueIterator.next();

            // データプロパティ・値関係
            DataPropertyValueRelation dataPropertyValueRelation = new DataPropertyValueRelation();
            // データプロパティID
            dataPropertyValueRelation.setDatapropertyId(currentProperty.getId());
            // データ値ID
            dataPropertyValueRelation.setDatavalueId(currentValue.getId());
            // 保存日時
            dataPropertyValueRelation.setSavedDateTime(LocalDateTime.now());
            // 作成済みデータプロパティ・値関係(IDを後続で使用する)
            var createdPropertyValueRelation = propertyValueRelationDomainService.create(dataPropertyValueRelation);
            createdPropertyValueRelations.add(createdPropertyValueRelation);
        }

        // 作成済みデータオブジェクト・データプロパティ・値関係保持用
        final List<DataObjectDataPropertyValueRelation> createdObjectPropertyValueRelations = new ArrayList<>();
        createdPropertyValueRelations.stream().forEachOrdered(propertyValueRelation -> {
            // データオブジェクト・データプロパティ・値関係
            DataObjectDataPropertyValueRelation objectPropertyValueRelation = new DataObjectDataPropertyValueRelation();
            // オブジェクトID
            objectPropertyValueRelation.setDataobjectId(createdDataObject.getId());
            // データプロパティ・値関係ID
            objectPropertyValueRelation.setDatapropertyValueRelationId(propertyValueRelation.getId());
            // 保存日時
            objectPropertyValueRelation.setSavedDateTime(LocalDateTime.now());

            // 作成済みデータオブジェクト・データプロパティ・値関係
            final DataObjectDataPropertyValueRelation createdObjectPropertyValueRelation = 
                objectPropertyValueRelationDomainService.create(objectPropertyValueRelation);
            createdObjectPropertyValueRelations.add(createdObjectPropertyValueRelation);
        });

        // 戻り値
        DataObjectInformation results = new DataObjectInformation();
        // 作成したデータオブジェクトID
        results.setDataobjectId(createdDataObject.getId());
        // データクラスID
        results.setDataclassId(createdDataObject.getDataclassId());
        // データプロパティ一覧
        results.setDataproperties(targetProperties);
        // 作成したデータ値一覧
        results.setDatavalues(createdValues);

        return results;
    }

    /**
     * データプロパティの一覧を取得する。
     * @return データプロパティの一覧
     */
    public List<DataObjectInformation> findAll() {
        // 戻り値保持用
        Map<Integer, DataObjectInformation> resultMap = new HashMap<>();

        // データオブジェクト・データプロパティ・値関係一覧
        final List<DataObjectDataPropertyValueRelation> objectPropertyValueRelations = objectPropertyValueRelationDomainService.findAll();
        objectPropertyValueRelations.stream().forEachOrdered(item -> {
            if(false == resultMap.containsKey(item.getDataobjectId())){
                // 必要に応じインスタンス生成・登録する
                var request = new DataObjectInformation();
                request.setDataproperties(new ArrayList<>());
                request.setDatavalues(new ArrayList<>());
                
                resultMap.put(item.getDataobjectId(), request);
            }

            // データオブジェクト情報の設定
            var dataObjectRequest = resultMap.get(item.getDataobjectId());
            dataObjectRequest.setDataobjectId(item.getDataobjectId());

            // データオブジェクト
            final Optional<DataObject> dataObject = 
                dataobjectDomainService.findById(item.getDataobjectId())
                    .stream()
                    .findFirst();

            dataObjectRequest.setDataclassId(dataObject.get().getDataclassId());

            // データプロパティ・値関係
            final Optional<DataPropertyValueRelation> propertyValueRelations = 
                propertyValueRelationDomainService.findById(item.getDatapropertyValueRelationId())
                    .stream()
                    .findFirst();

            // データプロパティ
            final Optional<DataProperty> dataproperty = 
                datapropertyDomainService.findById(propertyValueRelations.get().getDatapropertyId())
                    .stream()
                    .findFirst();

            dataObjectRequest.getDataproperties().add(dataproperty.get());

            // データ値
            final Optional<DataValue> datavalue = 
                dataValueDomainService.findById(propertyValueRelations.get().getDatavalueId())
                    .stream()
                    .findFirst();

            dataObjectRequest.getDatavalues().add(datavalue.get());
        });

        // オブジェクトIDで昇順ソートし、戻り値とする。
        return resultMap.entrySet().stream()
            .map(entrySet -> entrySet.getValue())
            .sorted(Comparator.comparingInt(DataObjectInformation::getDataobjectId))
            .collect(Collectors.toList());
    }
}
