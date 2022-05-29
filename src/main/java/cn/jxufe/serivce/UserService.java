package cn.jxufe.serivce;

import cn.jxufe.bean.EasyUIData;
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
    User findByUsername(String username);

    EasyUIData<User> findAllByUsernameLike(String username, Pageable pageable);

    User modify(User user);

    User register(User user);

    void delete(User user);

    boolean setCurUser(User user, HttpSession session);

    SeedBag purchaseSeed(int cropId, int seedNumber, User user);

    /**
     * 播种
     *
     * @param landId  土地id
     * @param cropId  作物id
     * @param session 操作用户
     * @return
     */
    boolean userActionPlantSeed(int landId, int cropId, HttpSession session);

    /**
     * 除虫
     *
     * @param landId  土地id
     * @param session 操作用户
     * @return
     */
    User userActionKillWorm(int landId, HttpSession session);

    /**
     * 收获
     *
     * @param landId  土地id
     * @param session 操作用户
     * @return
     */
    User userActionHarvest(int landId, HttpSession session);

    /**
     * 除枯草
     *
     * @param landId  土地id
     * @param session 操作用户
     * @return
     */
    User userActionCleanGrass(int landId, HttpSession session);
}
