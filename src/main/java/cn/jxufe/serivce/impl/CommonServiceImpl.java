package cn.jxufe.serivce.impl;

import cn.jxufe.entity.common.CropState;
import cn.jxufe.entity.common.LandType;
import cn.jxufe.entity.common.SeedType;
import cn.jxufe.repository.common.CropStateRepository;
import cn.jxufe.repository.common.LandTypeRepository;
import cn.jxufe.repository.common.SeedTypeRepository;
import cn.jxufe.serivce.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @create: 2022-05-19 10:05
 * @author: lwz
 * @description:
 **/
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    SeedTypeRepository seedTypeRepository;
    @Autowired
    LandTypeRepository landTypeRepository;
    @Autowired
    CropStateRepository cropStateRepository;

    @Override
    public List<SeedType> seedTypeFindAll() {
        return seedTypeRepository.findAll();
    }

    @Override
    public List<LandType> soilTypeFindAll() {
        return landTypeRepository.findAll();
    }

    @Override
    public List<CropState> cropStatusFindAll() {
        return cropStateRepository.findAll();
    }
}
