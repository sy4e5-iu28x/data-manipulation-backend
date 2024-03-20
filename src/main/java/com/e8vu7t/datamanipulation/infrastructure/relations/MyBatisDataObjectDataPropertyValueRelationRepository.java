package com.e8vu7t.datamanipulation.infrastructure.relations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.e8vu7t.datamanipulation.domain.relations.model.DataObjectDataPropertyValueRelation;
import com.e8vu7t.datamanipulation.domain.relations.service.DataObjectDataPropertyValueRelationRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MyBatisDataObjectDataPropertyValueRelationRepository implements DataObjectDataPropertyValueRelationRepository {

    /**
     * データオブジェクト・データプロパティ・値関係マッパー
     */
    private final DataObjectDataPropertyValueRelationMapper dataPropertyMapper;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(DataObjectDataPropertyValueRelation objectPropertyValueRelation) {
        dataPropertyMapper.insert(objectPropertyValueRelation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataObjectDataPropertyValueRelation> findAll() {
        return dataPropertyMapper.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataObjectDataPropertyValueRelation> findById(int id) {
        return dataPropertyMapper.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Integer> getMaxId() {
        return dataPropertyMapper.getMaxId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int update(DataObjectDataPropertyValueRelation objectPropertyValueRelation) {
        return dataPropertyMapper.update(objectPropertyValueRelation);
    }
}
