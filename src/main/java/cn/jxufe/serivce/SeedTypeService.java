package cn.jxufe.serivce;

import cn.jxufe.entity.common.SeedType;

import java.util.List;

/**
 * @create: 2022-05-05 13:14
 * @author: lwz
 * @description:
 **/
@Deprecated
public interface SeedTypeService {
    List<SeedType> findAll();

    SeedType save(SeedType seedType);

    void delete(SeedType seedType);
}
