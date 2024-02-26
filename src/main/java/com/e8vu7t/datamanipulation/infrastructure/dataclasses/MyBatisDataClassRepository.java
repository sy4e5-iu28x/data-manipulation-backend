package com.e8vu7t.datamanipulation.infrastructure.dataclasses;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.e8vu7t.datamanipulation.domain.dataclasses.model.DataClass;
import com.e8vu7t.datamanipulation.domain.dataclasses.service.DataClassRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MyBatisDataClassRepository implements DataClassRepository {

    /**
     * データクラスマッパー
     */
    private final DataClassMapper dataClassMapper;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(DataClass dataClass) {
        dataClassMapper.insert(dataClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataClass> findAll() {
        return dataClassMapper.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Integer> getMaxId() {
        return dataClassMapper.getMaxId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int update(DataClass dataClass) {
        return dataClassMapper.update(dataClass);
    }
}
