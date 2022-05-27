package cn.jxufe.serivce;

import cn.jxufe.bean.EasyUIData;
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

    EasyUIData<User> findAllByUsernameLike(String username, Pageable pageable);

    User modify(User user);

    User register(User user);

    void delete(User user);

    boolean setCurUser(HttpSession session, User user);

    void purchaseSeed(int seedId, User user, int seedNum);

    String userActionPlantSeed(int landId, int cId, HttpSession session);

    String userActionKillWorm(int landId, HttpSession session);

    String userActionHarvest(int landId, HttpSession session);

    String userActionCleanGrass(int landId, HttpSession session);

}
