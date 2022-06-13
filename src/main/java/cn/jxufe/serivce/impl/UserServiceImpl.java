package cn.jxufe.serivce.impl;

import cn.jxufe.bean.EasyUIData;
import cn.jxufe.bean.FarmResponse;
import cn.jxufe.bean.SystemCode;
import cn.jxufe.entity.Crop;
import cn.jxufe.entity.Land;
import cn.jxufe.entity.SeedBag;
import cn.jxufe.entity.User;
import cn.jxufe.entity.view.CropGrowView;
import cn.jxufe.repository.CropRepository;
import cn.jxufe.repository.LandRepository;
import cn.jxufe.repository.SeedBagRepository;
import cn.jxufe.repository.UserRepository;
import cn.jxufe.repository.view.CropGrowViewRepository;
import cn.jxufe.serivce.GameService;
import cn.jxufe.serivce.UserService;
import cn.jxufe.utils.PrintUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @create: 2022-05-11 08:37
 * @author: lwz
 * @description:
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    CropGrowViewRepository cropGrowViewRepository;
    @Autowired
    CropRepository cropRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SeedBagRepository seedBagRepository;
    @Autowired
    private GameService gameService;
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
    public EasyUIData<User> findAllByUsernameLikePageable(String username, Pageable pageable) {
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
        //如果未重名，或者修改的为本人
        if (hasUsername == null || Objects.equals(hasUsername.getUsername(), user.getUsername())) {
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    @Override
    public User changImgUrl(String username, String serverFilePath) {
        User userByFind = userRepository.findByUsername(username);
        userByFind.setHeadImgUrl(serverFilePath);
        return userRepository.save(userByFind);
    }


    /**
     * 注册用户，同时初始化用户游戏数据
     *
     * @param user 注册用户的信息
     * @return 注册成功返回用户信息，失败返回null
     */
    @Override
    public User register(User user) {
        User hasUsername = userRepository.findByUsername(user.getUsername());
        //如果重名
        if (hasUsername != null) {
            return null;
        } else {
            User savedUser = userRepository.save(user);
            List<Land> createLands = gameService.initiateUserLands(SystemCode.INITIATE_USER_LANDS, savedUser);//初始化玩家土地
            return savedUser;
        }
    }

    /**
     * 删除数据
     * 用户表User
     * 种子表SeedBag
     * 农场种植表UserLand
     *
     * @param user
     * @return
     */
    @Transactional
    @Override
    public void delete(User user) {
        User findUser = userRepository.findOne(user.getId());
        if (findUser != null) {
            userRepository.delete(findUser);//删除用户表数据
            seedBagRepository.deleteInBatch(seedBagRepository.findAllByUsername(findUser.getUsername()));//批量删除种子表有关用户的数据
            landRepository.deleteInBatch(landRepository.findAllByUsername(findUser.getUsername()));//批量删除农场种植表有关用户的数据
        }
    }

    /**
     * 用户登录
     * 查询数据库玩家信息，并将数据缓存至服务器中
     *
     * @param user
     * @param session
     * @return
     */
    @Override
    public User setCurUser(User user, HttpSession session) {
        User curUser = userRepository.findByUsername(user.getUsername());
        //查询到玩家数据
        if (curUser != null) {
            session.setAttribute(SystemCode.USER_SESSION_NAME, curUser);
            return curUser;
        } else {
            return null;
        }
    }

    /**
     * 玩家购买种子
     *
     * @param cropId 种子id
     * @param user   玩家信息
     */
    @Override
    public User purchaseSeed(int cropId, int seedNumber, User user) {
        User userByFind = userRepository.findByUsername(user.getUsername());
        Crop cropByFind = cropRepository.findByCropId(cropId);
        //玩家没有足够金钱
        if (userByFind.getMoney() - cropByFind.getPurchasePrice() * seedNumber < 0) {
            PrintUtil.println("钱：" + (userByFind.getMoney() - cropByFind.getPurchasePrice() * seedNumber));
            return null;
        } else {
            SeedBag seedBag = seedBagRepository.findByCropIdAndUsername(cropId, user.getUsername());
            if (seedBag != null) {
                //数据库中有相关字段，种子数量增加
                seedBag.setSeedNumber(seedBag.getSeedNumber() + seedNumber);
                seedBagRepository.save(seedBag);
            } else {
                //数据库中没有相关字段，创建关联
                SeedBag newSeedBag = new SeedBag();
                newSeedBag.setCropId(cropId);
                newSeedBag.setUsername(user.getUsername());
                newSeedBag.setSeedNumber(seedNumber);
                seedBagRepository.save(newSeedBag);
            }
            //扣除玩家金币
            userByFind.setMoney(userByFind.getMoney() - cropByFind.getPurchasePrice() * seedNumber);
            userRepository.save(userByFind);
            return userByFind;
        }
    }


    @Override
    public FarmResponse userActionPlantSeed(int landId, int cropId, HttpSession session) {
        Land landByFind = landRepository.findByLandId(landId);//获取土地信息
        //用户种子数量
        SeedBag seedBag = seedBagRepository.findByCropIdAndUsername(cropId, landByFind.getUsername());
        CropGrowView cropGrowViewByFind = cropGrowViewRepository.findByStageIdAndCropId(1, cropId);
        int seedNumber = seedBag.getSeedNumber();
        if (seedNumber <= 0) {
            //种子数量不足
            return null;
        } else if (landByFind.getLandTypeCode() != cropGrowViewByFind.getLandTypeCode()) {
            //土地类型和种子所需土地类型不一致
            return null;
        } else {
            if (seedNumber - 1 == 0) {
                seedBagRepository.delete(seedBag);//删除数据
            } else {
                seedBag.setSeedNumber(seedNumber - 1);
                seedBagRepository.save(seedBag);//保存种子收纳袋数据
            }
            landByFind.setCropId(cropId);
            landByFind.setHasCrop(1);
            landByFind.setHasInsect(0);
            landByFind.setIsWithered(0);
            landByFind.setIsMature(0);
            landByFind.setOutput(cropGrowViewByFind.getHarvestNum());//土地种子产出
            landByFind.setNowCropGrowStage(0);
            //TODO 可能超出范围
            landByFind.setNextCropGrowStage(1);
            landByFind.setGrowingSeason(cropGrowViewByFind.getGrowSeason());
            landByFind.setGrowthTimeOfEachState(0);
            landByFind.setStateEndTime(new Date(new Date().getTime() + cropGrowViewByFind.getGrowTime() * 1000L));
            landRepository.save(landByFind);//保存土地数据
            return new FarmResponse(SystemCode.FARM_RESPONSE_CODE_B, landByFind,
                    cropGrowViewRepository.findByStageIdAndCropIdOrderByStageIdAsc(landByFind.getNowCropGrowStage(),
                            landByFind.getCropId()));
        }
    }

    @Override
    public FarmResponse userActionKillWorm(int landId, HttpSession session) {
        Land landByFind = landRepository.findByLandId(landId);
        landByFind.setHasInsect(0);
        landRepository.save(landByFind);//保存土地数据
        //用户收益
        User userByFind = userRepository.findByUsername(landByFind.getUsername());
        userByFind.setExp(userByFind.getExp() + SystemCode.KILL_WORM_GAIN_EXP);
        userByFind.setPoint(userByFind.getPoint() + SystemCode.KILL_WORM_GAIN_POINT);
        userByFind.setMoney(userByFind.getMoney() + SystemCode.KILL_WORM_GAIN_MONEY);
        userRepository.save(userByFind);//保存用户数据
        //发送数据
        User userSend = new User();
        userSend.setExp(SystemCode.KILL_WORM_GAIN_EXP);
        userSend.setPoint(SystemCode.KILL_WORM_GAIN_POINT);
        userSend.setMoney(SystemCode.KILL_WORM_GAIN_MONEY);
        return new FarmResponse(SystemCode.FARM_RESPONSE_CODE_B, landByFind,
                cropGrowViewRepository.findByStageIdAndCropIdOrderByStageIdAsc(landByFind.getNowCropGrowStage(),
                        landByFind.getCropId()), userSend);
    }

    @Override
    public FarmResponse userActionHarvest(int landId, HttpSession session) {
        Land landByFind = landRepository.findByLandId(landId);
        Crop cropByFind = cropRepository.findByCropId(landByFind.getCropId());
        //如果作物生长阶段为成熟期
        if (landByFind.getIsMature() == 1) {
            landByFind.setIsMature(0);
            //如果作物的生长季度没有超过
            int growingSeason = landByFind.getGrowingSeason();
            if (growingSeason < cropByFind.getGrowSeason()) {
                landByFind.setNowCropGrowStage(0);
                landByFind.setGrowingSeason(growingSeason + 1);//进入下一季
                landRepository.save(landByFind);//保存数据
            } else {
                //进入枯草期间
                landByFind.setIsWithered(1);
                landByFind.setNowCropGrowStage(SystemCode.CROP_GROW_STAGE_IS_MATURE + 1);//枯草期间
                landRepository.save(landByFind);//保存数据
            }
            //用户收益
            User userByFind = userRepository.findByUsername(landByFind.getUsername());
            userByFind.setExp(userByFind.getExp() + cropByFind.getHarvestExp());
            userByFind.setPoint(userByFind.getPoint() + landByFind.getOutput());
            userByFind.setMoney(userByFind.getMoney() + landByFind.getOutput() * cropByFind.getSalePrice());
            userRepository.save(userByFind);//保存用户数据
            //发送数据
            User userSend = new User();
            userSend.setExp(cropByFind.getHarvestExp());
            userSend.setPoint(cropByFind.getHarvestScore());
            userSend.setMoney(landByFind.getOutput() * cropByFind.getSalePrice());
            return new FarmResponse(SystemCode.FARM_RESPONSE_CODE_B, landByFind,
                    cropGrowViewRepository.findByStageIdAndCropIdOrderByStageIdAsc(landByFind.getNowCropGrowStage(),
                            landByFind.getCropId()), userSend);
        } else {
            return null;
        }
    }

    @Override
    public FarmResponse userActionCleanGrass(int landId, HttpSession session) {
        Land landByFind = landRepository.findByLandId(landId);
        //清空数据
        Land land = new Land();
        land.setId(landByFind.getId());
        land.setLandId(landByFind.getLandId());
        land.setLandTypeCode(landByFind.getLandTypeCode());
        land.setUsername(landByFind.getUsername());
        PrintUtil.println("land: " + land);
        landRepository.save(land);//保存数据
        //用户收益
        User userByFind = userRepository.findByUsername(landByFind.getUsername());
        userByFind.setExp(userByFind.getExp() + SystemCode.CLEAN_GRASS_GAIN_EXP);
        userByFind.setPoint(userByFind.getPoint() + SystemCode.CLEAN_GRASS_GAIN_POINT);
        userByFind.setMoney(userByFind.getMoney() + SystemCode.CLEAN_GRASS_GAIN_MONEY);
        userRepository.save(userByFind);//保存用户数据
        //发送数据
        User userSend = new User();
        userSend.setExp(SystemCode.CLEAN_GRASS_GAIN_EXP);
        userSend.setPoint(SystemCode.CLEAN_GRASS_GAIN_POINT);
        userSend.setMoney(SystemCode.CLEAN_GRASS_GAIN_MONEY);
        return new FarmResponse(SystemCode.FARM_RESPONSE_CODE_B, land,
                cropGrowViewRepository.findByStageIdAndCropIdOrderByStageIdAsc(landByFind.getNowCropGrowStage(),
                        landByFind.getCropId()), userSend);
    }
}
