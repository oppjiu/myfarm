package cn.jxufe.repository.view;

import cn.jxufe.entity.view.SeedBagView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @create: 2022-05-26 09:33
 * @author: lwz
 * @description:
 **/
public interface SeedBagViewRepository extends JpaRepository<SeedBagView, Long> {
    Page<SeedBagView> findAllByUsername(String username, Pageable pageable);

    List<SeedBagView> findAllByUsername(String username);

    SeedBagView findByUsername(String username);

    SeedBagView findByCropId(int cropId);
}
