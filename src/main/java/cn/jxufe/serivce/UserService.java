package cn.jxufe.serivce;

import cn.jxufe.bean.EasyUIData;
import cn.jxufe.bean.FarmResponse;
import cn.jxufe.entity.SeedBag;
import cn.jxufe.entity.User;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @create: 2022-05-11 08:37
 * @author: lwz
 * @description:
 **/
public interface UserService {
    List<User> findAll();

    User findByUsername(String username);

    EasyUIData<User> findAllByUsernameLikePageable(String username, Pageable pageable);

    User modify(User user);

    User changImgUrl(String username, String serverFilePath);

    User register(User user);

    void delete(User user);

    User setCurUser(User user, HttpSession session);

    User purchaseSeed(int cropId, int seedNumber, User user);

    /**
     * 播种
     *
     * @param landId  土地id
     * @param cropId  作物id
     * @param session 操作用户
     * @return
     */
    FarmResponse userActionPlantSeed(int landId, int cropId, HttpSession session);

    /**
     * 除虫
     *
     * @param landId  土地id
     * @param session 操作用户
     * @return
     */
    FarmResponse userActionKillWorm(int landId, HttpSession session);

    /**
     * 收获
     *
     * @param landId  土地id
     * @param session 操作用户
     * @return
     */
    FarmResponse userActionHarvest(int landId, HttpSession session);

    /**
     * 除枯草
     *
     * @param landId  土地id
     * @param session 操作用户
     * @return
     */
    FarmResponse userActionCleanGrass(int landId, HttpSession session);
}
