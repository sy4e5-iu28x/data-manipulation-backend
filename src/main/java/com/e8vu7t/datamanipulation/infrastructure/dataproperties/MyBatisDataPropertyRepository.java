package com.e8vu7t.datamanipulation.infrastructure.dataproperties;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.e8vu7t.datamanipulation.domain.dataproperties.model.DataProperty;
import com.e8vu7t.datamanipulation.domain.dataproperties.service.DataPropertyRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MyBatisDataPropertyRepository implements DataPropertyRepository {

    /**
     * データプロパティマッパー
     */
    private final DataPropertyMapper dataPropertyMapper;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(DataProperty dataProperty) {
        dataPropertyMapper.insert(dataProperty);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataProperty> findAll() {
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
    public int update(DataProperty dataProperty) {
        return dataPropertyMapper.update(dataProperty);
    }
}
