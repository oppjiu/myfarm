package cn.jxufe.serivce.impl;


import cn.jxufe.entity.common.CropState;
import cn.jxufe.repository.common.CropStateRepository;
import cn.jxufe.serivce.CropStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @create: 2022-05-05 13:14
 * @author: lwz
 * @description:
 **/
@Service
@Deprecated
public class CropStateServiceImpl implements CropStateService {
    @Autowired
    public CropStateRepository cropStateRepository;

    @Override
    public List<CropState> findAll() {
        return cropStateRepository.findAll();
    }

    @Override
    public CropState save(CropState cropState) {
        return cropStateRepository.save(cropState);
    }

    @Override
    public void delete(CropState cropState) {
        cropStateRepository.delete(cropState);
    }
}
