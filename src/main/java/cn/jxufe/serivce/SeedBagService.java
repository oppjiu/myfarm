package cn.jxufe.serivce;

import cn.jxufe.bean.EasyUIData;
import cn.jxufe.entity.SeedBag;
import cn.jxufe.entity.view.SeedBagView;
import org.springframework.data.domain.Pageable;

/**
 * @create: 2022-05-26 09:46
 * @author: lwz
 * @description:
 **/
public interface SeedBagService {
    EasyUIData<SeedBagView> findAllByUsernamePageable(String username, Pageable pageable);

    SeedBag save(SeedBag seedBag);

    void delete(SeedBag seedBag);
}
