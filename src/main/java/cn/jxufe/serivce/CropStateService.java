package cn.jxufe.serivce;

import cn.jxufe.entity.common.CropState;

import java.util.List;

/**
 * @create: 2022-05-05 13:14
 * @author: lwz
 * @description:
 **/
@Deprecated
public interface CropStateService {
    List<CropState> findAll();

    CropState save(CropState cropState);

    void delete(CropState cropState);
}
