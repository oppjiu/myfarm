package cn.jxufe.serivce;

import cn.jxufe.entity.common.CropState;
import cn.jxufe.entity.common.LandType;
import cn.jxufe.entity.common.SeedType;

import java.util.List;

/**
 * @create: 2022-05-19 10:05
 * @author: lwz
 * @description:
 **/
public interface CommonService {
    List<SeedType> seedTypeFindAll();

    List<LandType> soilTypeFindAll();

    List<CropState> cropStatusFindAll();
}
