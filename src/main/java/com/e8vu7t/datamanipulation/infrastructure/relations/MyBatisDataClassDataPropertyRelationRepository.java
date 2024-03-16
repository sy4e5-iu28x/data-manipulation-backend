package com.e8vu7t.datamanipulation.infrastructure.relations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.e8vu7t.datamanipulation.domain.relations.model.DataClassDataPropertyRelation;
import com.e8vu7t.datamanipulation.domain.relations.service.DataClassDataPropertyRelationRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MyBatisDataClassDataPropertyRelationRepository implements DataClassDataPropertyRelationRepository {

    /**
     * データクラス・データプロパティ関係マッパー
     */
    private final DataClassDataPropertyRelationMapper dataPropertyMapper;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(DataClassDataPropertyRelation classPropertyRelation) {
        dataPropertyMapper.insert(classPropertyRelation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataClassDataPropertyRelation> findAll() {
        return dataPropertyMapper.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataClassDataPropertyRelation> findByDataClassId(int dataClassId){
        return dataPropertyMapper.findByDataClassId(dataClassId);
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
    public int update(DataClassDataPropertyRelation classPropertyRelation) {
        return dataPropertyMapper.update(classPropertyRelation);
    }
}
