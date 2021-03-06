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
     * ??????????????????
     *
     * @param user ?????????????????????
     * @return ?????????????????????????????????????????????null
     */
    @Override
    public User modify(User user) {
        User hasUsername = userRepository.findByUsername(user.getUsername());
        //??????????????????????????????????????????
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
     * ????????????????????????????????????????????????
     *
     * @param user ?????????????????????
     * @return ?????????????????????????????????????????????null
     */
    @Override
    public User register(User user) {
        User hasUsername = userRepository.findByUsername(user.getUsername());
        //????????????
        if (hasUsername != null) {
            return null;
        } else {
            User savedUser = userRepository.save(user);
            List<Land> createLands = gameService.initiateUserLands(SystemCode.INITIATE_USER_LANDS, savedUser);//?????????????????????
            return savedUser;
        }
    }

    /**
     * ????????????
     * ?????????User
     * ?????????SeedBag
     * ???????????????UserLand
     *
     * @param user
     * @return
     */
    @Transactional
    @Override
    public void delete(User user) {
        User findUser = userRepository.findOne(user.getId());
        if (findUser != null) {
            userRepository.delete(findUser);//?????????????????????
            seedBagRepository.deleteInBatch(seedBagRepository.findAllByUsername(findUser.getUsername()));//??????????????????????????????????????????
            landRepository.deleteInBatch(landRepository.findAllByUsername(findUser.getUsername()));//????????????????????????????????????????????????
        }
    }

    /**
     * ????????????
     * ???????????????????????????????????????????????????????????????
     *
     * @param user
     * @param session
     * @return
     */
    @Override
    public User setCurUser(User user, HttpSession session) {
        User curUser = userRepository.findByUsername(user.getUsername());
        //?????????????????????
        if (curUser != null) {
            session.setAttribute(SystemCode.USER_SESSION_NAME, curUser);
            return curUser;
        } else {
            return null;
        }
    }

    /**
     * ??????????????????
     *
     * @param cropId ??????id
     * @param user   ????????????
     */
    @Override
    public User purchaseSeed(int cropId, int seedNumber, User user) {
        User userByFind = userRepository.findByUsername(user.getUsername());
        Crop cropByFind = cropRepository.findByCropId(cropId);
        //????????????????????????
        if (userByFind.getMoney() - cropByFind.getPurchasePrice() * seedNumber < 0) {
            PrintUtil.println("??????" + (userByFind.getMoney() - cropByFind.getPurchasePrice() * seedNumber));
            return null;
        } else {
            SeedBag seedBag = seedBagRepository.findByCropIdAndUsername(cropId, user.getUsername());
            if (seedBag != null) {
                //????????????????????????????????????????????????
                seedBag.setSeedNumber(seedBag.getSeedNumber() + seedNumber);
                seedBagRepository.save(seedBag);
            } else {
                //?????????????????????????????????????????????
                SeedBag newSeedBag = new SeedBag();
                newSeedBag.setCropId(cropId);
                newSeedBag.setUsername(user.getUsername());
                newSeedBag.setSeedNumber(seedNumber);
                seedBagRepository.save(newSeedBag);
            }
            //??????????????????
            userByFind.setMoney(userByFind.getMoney() - cropByFind.getPurchasePrice() * seedNumber);
            userRepository.save(userByFind);
            return userByFind;
        }
    }


    @Override
    public FarmResponse userActionPlantSeed(int landId, int cropId, HttpSession session) {
        Land landByFind = landRepository.findByLandId(landId);//??????????????????
        //??????????????????
        SeedBag seedBag = seedBagRepository.findByCropIdAndUsername(cropId, landByFind.getUsername());
        CropGrowView cropGrowViewByFind = cropGrowViewRepository.findByStageIdAndCropId(1, cropId);
        int seedNumber = seedBag.getSeedNumber();
        if (seedNumber <= 0) {
            //??????????????????
            return null;
        } else if (landByFind.getLandTypeCode() != cropGrowViewByFind.getLandTypeCode()) {
            //????????????????????????????????????????????????
            return null;
        } else {
            if (seedNumber - 1 == 0) {
                seedBagRepository.delete(seedBag);//????????????
            } else {
                seedBag.setSeedNumber(seedNumber - 1);
                seedBagRepository.save(seedBag);//???????????????????????????
            }
            landByFind.setCropId(cropId);
            landByFind.setHasCrop(1);
            landByFind.setHasInsect(0);
            landByFind.setIsWithered(0);
            landByFind.setIsMature(0);
            landByFind.setOutput(cropGrowViewByFind.getHarvestNum());//??????????????????
            landByFind.setNowCropGrowStage(0);
            //TODO ??????????????????
            landByFind.setNextCropGrowStage(1);
            landByFind.setGrowingSeason(cropGrowViewByFind.getGrowSeason());
            landByFind.setGrowthTimeOfEachState(0);
            landByFind.setStateEndTime(new Date(new Date().getTime() + cropGrowViewByFind.getGrowTime() * 1000L));
            landRepository.save(landByFind);//??????????????????
            return new FarmResponse(SystemCode.FARM_RESPONSE_CODE_B, landByFind,
                    cropGrowViewRepository.findByStageIdAndCropIdOrderByStageIdAsc(landByFind.getNowCropGrowStage(),
                            landByFind.getCropId()));
        }
    }

    @Override
    public FarmResponse userActionKillWorm(int landId, HttpSession session) {
        Land landByFind = landRepository.findByLandId(landId);
        landByFind.setHasInsect(0);
        landRepository.save(landByFind);//??????????????????
        //????????????
        User userByFind = userRepository.findByUsername(landByFind.getUsername());
        userByFind.setExp(userByFind.getExp() + SystemCode.KILL_WORM_GAIN_EXP);
        userByFind.setPoint(userByFind.getPoint() + SystemCode.KILL_WORM_GAIN_POINT);
        userByFind.setMoney(userByFind.getMoney() + SystemCode.KILL_WORM_GAIN_MONEY);
        userRepository.save(userByFind);//??????????????????
        //????????????
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
        //????????????????????????????????????
        if (landByFind.getIsMature() == 1) {
            landByFind.setIsMature(0);
            //???????????????????????????????????????
            int growingSeason = landByFind.getGrowingSeason();
            if (growingSeason < cropByFind.getGrowSeason()) {
                landByFind.setNowCropGrowStage(0);
                landByFind.setGrowingSeason(growingSeason + 1);//???????????????
                landRepository.save(landByFind);//????????????
            } else {
                //??????????????????
                landByFind.setIsWithered(1);
                landByFind.setNowCropGrowStage(SystemCode.CROP_GROW_STAGE_IS_MATURE + 1);//????????????
                landRepository.save(landByFind);//????????????
            }
            //????????????
            User userByFind = userRepository.findByUsername(landByFind.getUsername());
            userByFind.setExp(userByFind.getExp() + cropByFind.getHarvestExp());
            userByFind.setPoint(userByFind.getPoint() + landByFind.getOutput());
            userByFind.setMoney(userByFind.getMoney() + landByFind.getOutput() * cropByFind.getSalePrice());
            userRepository.save(userByFind);//??????????????????
            //????????????
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
        //????????????
        Land land = new Land();
        land.setId(landByFind.getId());
        land.setLandId(landByFind.getLandId());
        land.setLandTypeCode(landByFind.getLandTypeCode());
        land.setUsername(landByFind.getUsername());
        PrintUtil.println("land: " + land);
        landRepository.save(land);//????????????
        //????????????
        User userByFind = userRepository.findByUsername(landByFind.getUsername());
        userByFind.setExp(userByFind.getExp() + SystemCode.CLEAN_GRASS_GAIN_EXP);
        userByFind.setPoint(userByFind.getPoint() + SystemCode.CLEAN_GRASS_GAIN_POINT);
        userByFind.setMoney(userByFind.getMoney() + SystemCode.CLEAN_GRASS_GAIN_MONEY);
        userRepository.save(userByFind);//??????????????????
        //????????????
        User userSend = new User();
        userSend.setExp(SystemCode.CLEAN_GRASS_GAIN_EXP);
        userSend.setPoint(SystemCode.CLEAN_GRASS_GAIN_POINT);
        userSend.setMoney(SystemCode.CLEAN_GRASS_GAIN_MONEY);
        return new FarmResponse(SystemCode.FARM_RESPONSE_CODE_B, land,
                cropGrowViewRepository.findByStageIdAndCropIdOrderByStageIdAsc(landByFind.getNowCropGrowStage(),
                        landByFind.getCropId()), userSend);
    }
}
