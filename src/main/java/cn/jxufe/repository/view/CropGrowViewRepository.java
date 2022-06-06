package cn.jxufe.repository.view;

import cn.jxufe.entity.view.CropGrowView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @create: 2022-05-05 10:25
 * @author: lwz
 * @description:
 **/
@Repository
public interface CropGrowViewRepository extends JpaRepository<CropGrowView, Long> {
    Page<CropGrowView> findAllByCropId(int cropId, Pageable pageable);

    CropGrowView findByStageIdAndCropId(int stageId, int cropId);

    CropGrowView findByStageIdAndCropIdOrderByStageIdAsc(int stageId, int cropId);
}
