package cn.jxufe.repository.view;

import cn.jxufe.entity.view.CropView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @create: 2022-05-05 10:24
 * @author: lwz
 * @description:
 **/

public interface CropViewRepository extends JpaRepository<CropView, Long> {

    /**
     * 种子名称模糊查询
     *
     * @param name     种子名称
     * @param pageable 可分页
     * @return 分页数据
     */
    Page<CropView> findAllByCropNameContaining(String name, Pageable pageable);

}
