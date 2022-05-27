package cn.jxufe.serivce;

import cn.jxufe.bean.EasyUIData;
import cn.jxufe.entity.CropGrow;
import cn.jxufe.entity.view.CropGrowView;
import org.springframework.data.domain.Pageable;

/**
 * @create: 2022-05-05 10:22
 * @author: lwz
 * @description:
 **/
public interface CropGrowService {
    EasyUIData<CropGrowView> findAllPageable(Pageable pageable);

    CropGrow save(CropGrow cropGrow);

    void delete(CropGrow cropGrow);
}
