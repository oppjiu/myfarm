package cn.jxufe.serivce.impl;


import cn.jxufe.bean.EasyUIData;
import cn.jxufe.entity.Crop;
import cn.jxufe.entity.CropGrow;
import cn.jxufe.entity.view.CropView;
import cn.jxufe.repository.CropGrowRepository;
import cn.jxufe.repository.CropRepository;
import cn.jxufe.repository.view.CropViewRepository;
import cn.jxufe.serivce.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @create: 2022-05-05 10:51
 * @author: lwz
 * @description:
 **/
@Service
public class CropServiceImpl implements CropService {
    @Autowired
    public CropViewRepository cropViewRepository;
    @Autowired
    public CropRepository cropRepository;
    @Autowired
    public CropGrowRepository cropGrowRepository;

    @Override
    public EasyUIData<CropView> findAllPageableByCropNameLike(String name, Pageable pageable) {
        Page<CropView> page = cropViewRepository.findAllByCropNameContaining(name, pageable);
        EasyUIData<CropView> easyUIData = new EasyUIData<CropView>();
        easyUIData.setTotal(page.getTotalElements());
        easyUIData.setRows(page.getContent());
        return easyUIData;
    }

    @Override
    public Crop save(Crop crop) {
        return cropRepository.save(crop);
    }

    /**
     * 删除种子以及种子生长阶段数据
     *
     * @param crop 种子信息（需含有种子id）
     */
    @Transactional
    @Override
    public void delete(Crop crop) {
        Crop cropByFind = cropRepository.findOne(crop.getId());
        List<CropGrow> cropGrowList = cropGrowRepository.findAllByCropId(cropByFind.getCropId());
        cropGrowRepository.deleteInBatch(cropGrowList);
        cropRepository.delete(cropByFind);
    }
}
