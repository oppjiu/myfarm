package cn.jxufe.repository;

import cn.jxufe.entity.SeedBag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @create: 2022-05-18 10:46
 * @author: lwz
 * @description:
 **/
public interface SeedBagRepository extends JpaRepository<SeedBag, Long> {
    List<SeedBag> findAllByUsername(String username);

    SeedBag findByUsername(String username);

    SeedBag findByCropId(int cropId);

    SeedBag findByCropIdAndUsername(int cropId, String username);
}
