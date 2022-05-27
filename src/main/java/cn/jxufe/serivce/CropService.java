package cn.jxufe.serivce;

import cn.jxufe.bean.EasyUIData;
import cn.jxufe.entity.Crop;
import cn.jxufe.entity.view.CropView;
import org.springframework.data.domain.Pageable;

/**
 * @create: 2022-05-05 10:22
 * @author: lwz
 * @description:
 **/
public interface CropService {
    EasyUIData<CropView> findAllByCropNameLike(String name, Pageable pageable);

    Crop save(Crop crop);

    void delete(Crop crop);
}
