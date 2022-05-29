package cn.jxufe.repository.view;

import cn.jxufe.entity.view.LandView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @create: 2022-05-26 22:31
 * @author: lwz
 * @description:
 **/
public interface LandViewRepository extends JpaRepository<LandView, Long> {
    /**
     * 查找条件为是否种植和是否枯萎的土地
     *
     * @param hasCrop    是否种植
     * @param isWithered 是否枯萎
     * @return
     */
    List<LandView> findAllByHasCropAndIsWitheredAndIsMature(int hasCrop, int isWithered, int isMature);

    LandView findByLandId(int id);
}
