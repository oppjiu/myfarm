package cn.jxufe.serivce.impl;


import cn.jxufe.bean.EasyUIData;
import cn.jxufe.entity.Crop;
import cn.jxufe.entity.view.CropView;
import cn.jxufe.repository.CropRepository;
import cn.jxufe.repository.view.CropViewRepository;
import cn.jxufe.serivce.CropService;
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
public class CropServiceImpl implements CropService {
    @Autowired
    public CropViewRepository cropViewRepository;
    @Autowired
    public CropRepository cropRepository;

    @Override
    public EasyUIData<CropView> findAllByCropNameLike(String name, Pageable pageable) {
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

    @Override
    public void delete(Crop crop) {
        cropRepository.delete(crop);
    }
}
