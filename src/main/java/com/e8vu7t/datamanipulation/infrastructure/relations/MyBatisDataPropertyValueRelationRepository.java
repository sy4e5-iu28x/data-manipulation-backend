package com.e8vu7t.datamanipulation.infrastructure.relations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.e8vu7t.datamanipulation.domain.relations.model.DataPropertyValueRelation;
import com.e8vu7t.datamanipulation.domain.relations.service.DataPropertyValueRelationRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MyBatisDataPropertyValueRelationRepository implements DataPropertyValueRelationRepository {

    /**
     * データプロパティ・値関係マッパー
     */
    private final DataPropertyValueRelationMapper dataPropertyMapper;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(DataPropertyValueRelation propertyValueRelation) {
        dataPropertyMapper.insert(propertyValueRelation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataPropertyValueRelation> findAll() {
        return dataPropertyMapper.findAll();
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
    public int update(DataPropertyValueRelation propertyValueRelation) {
        return dataPropertyMapper.update(propertyValueRelation);
    }
}
