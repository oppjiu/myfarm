package cn.jxufe.repository.view;

import cn.jxufe.entity.view.CropGrowView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @create: 2022-05-05 10:25
 * @author: lwz
 * @description:
 **/
@Repository
public interface CropGrowViewRepository extends JpaRepository<CropGrowView, Long> {
}
