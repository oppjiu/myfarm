package cn.jxufe.serivce;

import cn.jxufe.bean.EasyUIData;
import cn.jxufe.entity.SeedBag;
import cn.jxufe.entity.view.SeedBagView;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @create: 2022-05-26 09:46
 * @author: lwz
 * @description:
 **/
public interface SeedBagService {
    List<SeedBagView> findAll();

    EasyUIData<SeedBagView> findAllPageable(Pageable pageable);

    SeedBag save(SeedBag seedBag);

    void delete(SeedBag seedBag);
}
