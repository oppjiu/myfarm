package cn.jxufe.repository;

import cn.jxufe.entity.CropGrow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @create: 2022-05-26 10:14
 * @author: lwz
 * @description:
 **/
public interface CropGrowRepository extends JpaRepository<CropGrow, Long> {

    List<CropGrow> findAllByCropId(int cropId);

    CropGrow findByStageIdAndCropId(int stageId, int cropId);
    CropGrow findByStageIdAndId(int stageId, long id);
}
