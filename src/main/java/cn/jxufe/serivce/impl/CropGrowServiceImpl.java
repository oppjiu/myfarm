package cn.jxufe.serivce.impl;

import cn.jxufe.bean.EasyUIData;
import cn.jxufe.entity.CropGrow;
import cn.jxufe.entity.view.CropGrowView;
import cn.jxufe.repository.CropGrowRepository;
import cn.jxufe.repository.view.CropGrowViewRepository;
import cn.jxufe.serivce.CropGrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @create: 2022-05-05 10:51
 * @author: lwz
 * @description:
 **/
@Service
public class CropGrowServiceImpl implements CropGrowService {
    @Autowired
    public CropGrowViewRepository cropGrowViewRepository;
    @Autowired
    public CropGrowRepository cropGrowRepository;

    /**
     * 查询生长作物信息
     *
     * @param pageable
     * @return
     */
    @Override
    public EasyUIData<CropGrowView> findAllPageableByCropId(int cropId, Pageable pageable) {
        Page<CropGrowView> page = cropGrowViewRepository.findAllByCropId(cropId, pageable);
        EasyUIData<CropGrowView> easyUIData = new EasyUIData<CropGrowView>();
        easyUIData.setTotal(page.getTotalElements());
        easyUIData.setRows(page.getContent());
        return easyUIData;
    }

    @Override
    public CropGrow save(CropGrow cropGrow) {
        return cropGrowRepository.save(cropGrow);
    }

    @Override
    public void delete(CropGrow cropGrow) {
        cropGrowRepository.delete(cropGrowRepository.findByStageIdAndCropId(cropGrow.getStageId(), cropGrow.getCropId()));
    }
}
