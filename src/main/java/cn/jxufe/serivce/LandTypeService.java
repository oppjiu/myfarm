package cn.jxufe.serivce;

import cn.jxufe.entity.common.LandType;

import java.util.List;

/**
 * @create: 2022-05-05 13:15
 * @author: lwz
 * @description:
 **/
@Deprecated
public interface LandTypeService {
    List<LandType> findAll();

    LandType save(LandType landType);

    void delete(LandType landType);
}
