package cn.jxufe.serivce.impl;

import cn.jxufe.entity.common.SeedType;
import cn.jxufe.repository.common.SeedTypeRepository;
import cn.jxufe.serivce.SeedTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @create: 2022-05-05 13:18
 * @author: lwz
 * @description:
 **/
@Service
@Deprecated
public class SeedTypeServiceImpl implements SeedTypeService {

    @Autowired
    public SeedTypeRepository seedTypeRepository;

    @Override
    public List<SeedType> findAll() {
        return seedTypeRepository.findAll();
    }

    @Override
    public SeedType save(SeedType seedType) {
        return seedTypeRepository.save(seedType);
    }

    @Override
    public void delete(SeedType seedType) {
        seedTypeRepository.delete(seedType);
    }
}
