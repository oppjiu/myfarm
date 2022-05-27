package cn.jxufe.repository.view;

import cn.jxufe.entity.view.SeedBagView;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @create: 2022-05-26 09:33
 * @author: lwz
 * @description:
 **/
public interface SeedBagViewRepository extends JpaRepository<SeedBagView, Long> {
    SeedBagView findByUsername(String username);

    SeedBagView findByCropId(int cropId);
}
