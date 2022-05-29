package cn.jxufe.repository;

import cn.jxufe.entity.CropGrow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @create: 2022-05-26 10:14
 * @author: lwz
 * @description:
 **/
public interface CropGrowRepository extends JpaRepository<CropGrow, Long> {
    Page<CropGrow> findAllByCropId(int cropId, Pageable pageable);

    CropGrow findByStageIdAndCropId(int stageId, int cropId);
}
