package cn.jxufe.repository;

import cn.jxufe.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @create: 2022-05-26 10:14
 * @author: lwz
 * @description:
 **/
public interface CropRepository extends JpaRepository<Crop, Long> {
    List<Crop> findAllByCropId(int cropId);

    Crop findByCropId(int cropId);
}
