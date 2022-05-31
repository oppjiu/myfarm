package cn.jxufe.serivce;

import cn.jxufe.entity.Land;
import cn.jxufe.entity.view.LandView;

import java.util.List;

/**
 * @create: 2022-05-26 22:32
 * @author: lwz
 * @description:
 **/
public interface LandService {
    List<LandView> findAll();

    Land save(Land land);

    void delete(Land land);
}
