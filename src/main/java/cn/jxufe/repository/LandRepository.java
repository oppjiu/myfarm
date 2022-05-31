package cn.jxufe.repository;

import cn.jxufe.entity.Land;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @create: 2022-05-26 22:29
 * @author: lwz
 * @description:
 **/
public interface LandRepository extends JpaRepository<Land, Long> {
    Land findByLandId(int id);

    List<Land> findAllByUsername(String username);

    List<Land> findAllByHasCrop(int hasCrop);

    @Query(value = "SELECT max(landId) FROM Land ")
    Integer getMaxLandIdIndex();

}
