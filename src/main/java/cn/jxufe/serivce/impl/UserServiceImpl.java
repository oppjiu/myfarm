package cn.jxufe.serivce.impl;

import cn.jxufe.bean.EasyUIData;
import cn.jxufe.entity.SeedBag;
import cn.jxufe.entity.User;
import cn.jxufe.repository.LandRepository;
import cn.jxufe.repository.SeedBagRepository;
import cn.jxufe.repository.UserRepository;
import cn.jxufe.serivce.LandService;
import cn.jxufe.serivce.UserService;
import cn.jxufe.websocket.SystemWebsocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @create: 2022-05-11 08:37
 * @author: lwz
 * @description:
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private SystemWebsocketHandler systemWebsocketHandler;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SeedBagRepository seedBagRepository;
    @Autowired
    private LandService landService;
    @Autowired
    private LandRepository landRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public EasyUIData<User> findAllByUsernameLike(String username, Pageable pageable) {
        Page<User> page = userRepository.findAllByUsernameContaining(username, pageable);
        EasyUIData<User> easyUIData = new EasyUIData<User>();
        easyUIData.setTotal(page.getTotalElements());
        easyUIData.setRows(page.getContent());
        return easyUIData;
    }


    /**
     * 修改用户信息
     *
     * @param user 修改用户的信息
     * @return 修改成功返回用户信息，失败返回null
     */
    @Override
    public User modify(User user) {
        User hasUsername = userRepository.findByUsername(user.getUsername());
        //如果重名
        if (hasUsername != null) {
            return null;
        } else {
            return userRepository.save(user);
        }
    }


    /**
     * 注册用户，同时初始化用户游戏数据
     *
     * @param user 注册用户的信息
     * @return 注册成功返回用户信息，失败返回null
     */
    @Transactional
    @Override
    public User register(User user) {
        User hasUsername = userRepository.findByUsername(user.getUsername());
        if (hasUsername != null) {
            return null;
        } else {
            User savedUser = userRepository.save(user);
            landService.initiateUserLands(savedUser, 24);
            return savedUser;
        }
    }

    /**
     * 级联删除用户表User、种子表SeedBag、农场种植表UserLand
     *
     * @param user
     * @return
     */
    @Transactional
    @Override
    public void delete(User user) {
        User findUser = userRepository.findByUsername(user.getUsername());
        if (findUser != null) {
            //删除用户表数据
            userRepository.delete(findUser);
            //删除种子表有关用户的数据
            seedBagRepository.deleteInBatch(seedBagRepository.findAllByUsername(findUser.getUsername()));
            //农场种植表有关用户的数据
            landRepository.deleteInBatch(landRepository.findAllByUsername(findUser.getUsername()));
        }
    }


    /**
     * 切换用户
     * 查询数据库玩家信息，并将数据缓存至服务器中
     *
     * @param session
     * @param user
     * @return
     */
    @Override
    public boolean setCurUser(HttpSession session, User user) {
        User curUser = userRepository.findByUsername(user.getUsername());
        if (curUser != null) {
            //查询成功
            session.setAttribute("curUser", curUser);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 玩家购买种子
     *
     * @param cropId 种子id
     * @param user   玩家信息
     */
    @Override
    public void purchaseSeed(int cropId, User user, int seedNumber) {
        SeedBag seedBag = seedBagRepository.findByUserIdAndSeedId(cropId, user.getUsername());
        //如果数据库中有相关字段，种子数量加一
        if (seedBag != null) {
            seedBag.setSeedNumber(seedBag.getSeedNumber() + 1);
            seedBagRepository.save(seedBag);
        } else {
            //数据库没有相关字段，创建相关种子
            SeedBag seedBagView = new SeedBag();
            seedBagView.setCropId(cropId);
            seedBagView.setUsername(user.getUsername());
            seedBagView.setSeedNumber(seedNumber);
            seedBagRepository.save(seedBagView);
        }
    }

    @Override
    public String userActionPlantSeed(int landId, int cId, HttpSession session) {
        return null;
    }

    @Override
    public String userActionKillWorm(int landId, HttpSession session) {
        return null;
    }

    @Override
    public String userActionHarvest(int landId, HttpSession session) {
        return null;
    }

    @Override
    public String userActionCleanGrass(int landId, HttpSession session) {
        return null;
    }
}
