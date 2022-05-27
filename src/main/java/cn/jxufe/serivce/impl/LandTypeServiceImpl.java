package cn.jxufe.serivce.impl;

import cn.jxufe.entity.common.LandType;
import cn.jxufe.repository.common.LandTypeRepository;
import cn.jxufe.serivce.LandTypeService;
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
public class LandTypeServiceImpl implements LandTypeService {
    @Autowired
    private LandTypeRepository landTypeRepository;

    @Override
    public List<LandType> findAll() {
        return landTypeRepository.findAll();
    }

    @Override
    public LandType save(LandType landType) {
        return landTypeRepository.save(landType);
    }

    @Override
    public void delete(LandType landType) {
        landTypeRepository.delete(landType);
    }
}
