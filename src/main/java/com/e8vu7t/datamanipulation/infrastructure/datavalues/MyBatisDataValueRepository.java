package com.e8vu7t.datamanipulation.infrastructure.datavalues;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.e8vu7t.datamanipulation.domain.datavalues.model.DataValue;
import com.e8vu7t.datamanipulation.domain.datavalues.service.DataValueRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MyBatisDataValueRepository implements DataValueRepository {

    /**
     * データ値マッパー
     */
    private final DataValueMapper dataPropertyMapper;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(DataValue dataValue) {
        dataPropertyMapper.insert(dataValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataValue> findAll() {
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
    public int update(DataValue dataValue) {
        return dataPropertyMapper.update(dataValue);
    }
}
