package cn.jxufe.serivce.impl;

import cn.jxufe.bean.FarmResponse;
import cn.jxufe.bean.SystemCode;
import cn.jxufe.entity.CropGrow;
import cn.jxufe.entity.Land;
import cn.jxufe.entity.User;
import cn.jxufe.entity.view.LandView;
import cn.jxufe.repository.CropGrowRepository;
import cn.jxufe.repository.LandRepository;
import cn.jxufe.repository.UserRepository;
import cn.jxufe.repository.view.CropGrowViewRepository;
import cn.jxufe.repository.view.LandViewRepository;
import cn.jxufe.serivce.GameService;
import cn.jxufe.utils.PrintUtil;
import cn.jxufe.websocket.SystemWebsocketHandler;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

/**
 * @create: 2022-05-25 09:55
 * @author: lwz
 * @description:
 **/
@Service
public class GameServiceImpl implements GameService {
    @Autowired
    SystemWebsocketHandler systemWebsocketHandler;
    @Autowired
    LandViewRepository landViewRepository;
    @Autowired
    LandRepository landRepository;
    @Autowired
    CropGrowRepository cropGrowRepository;
    @Autowired
    CropGrowViewRepository cropGrowViewRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public void gameSeversInitiate() {
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
//                        systemWebsocketHandler.sendMessageToAll(new TextMessage("现在服务器时间是：" + new Date()));
                        //更新游戏数据
                        updateCropStage();
                        System.out.println("现在服务器时间是：" + new Date());
                    }
                }, 0, SystemCode.PER_TIME * 1000);
    }

    public boolean insectAlgorithm(Land land, CropGrow cropGrow, Date nowTime) {
        //TODO 当玩家在线时才生虫
        //TODO 不能在即将进入下一阶段的前一段时间内生虫
        for (WebSocketSession session : systemWebsocketHandler.sessionList) {
            User user = (User) session.getHandshakeAttributes().get(SystemCode.WEBSOCKET_SESSION_NAME);
            //用户在线
            if (Objects.equals(user.getUsername(), land.getUsername())) {
                //如果随机生成数值在生虫概率之内
                if (Math.random() < cropGrow.getInsectChance()) {
                    System.out.println("(land.getStateEndTime().getTime() - nowTime.getTime()) = " + (land.getStateEndTime().getTime() - nowTime.getTime()));
                    //距离下一阶段最多差5秒
                    return land.getStateEndTime().getTime() - nowTime.getTime() >= 5 * 1000;
                }
            }
        }
        return false;
    }

    /**
     * 播种：点击空白土地，弹出种子选择框，点选具体作物进行播种。每种作物有土地的类型要求，不能满足种子土地要求的地面不能进行播种。
     * 生长：服务器负责作物生长过程的计算，并在作物生长至新阶段时通知客户端更新UI数据。
     * 生虫：服务器负责在作物生长过程进行虫害计算，并在作物生虫时通知客户端更新UI数据。
     * 除虫：当作物生虫时需要及时在作物进入下一生长阶段前除虫，否则该作物将减少1至2个果实。
     * 收获：点击处于成熟状态的植物，对果实进行采摘，并根据收获数据更新用户头像信息。
     * 除枯叶：点击枯叶，从土地上清除枯叶。如果是单季作物，枯叶除去后，土地为空；如果是多季作物，枯叶除去后，土地自动播种，只到最后一季的枯叶除去后，土地才为空。
     */

    @Override
    public List<Land> initiateUserLands(int landNumber, User user) {
        Integer maxLandIdIndex = landRepository.getMaxLandIdIndex();
        int landId = 0;
        //数据库中数据返回null
        if (maxLandIdIndex != null) {
            landId = maxLandIdIndex + 1;
        }
        ArrayList<Land> landList = new ArrayList<>();
        for (int i = 0, landTypeCode = 1; i < landNumber; i++, landId++) {
            Land createLand = new Land();
            //TODO 随机生成id or 根据自增设置id
            PrintUtil.println("initiateUserLands: " + user);
            createLand.setLandId(landId);
            //TODO 测试数据
//            if (i == 0) {
//                createLand.setHasCrop(0);
//            } else if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6) {
//                createLand.setHasInsect(1);
//                createLand.setIsMature(1);
//                createLand.setNowCropGrowStage(5);
//                createLand.setHasCrop(1);
//                createLand.setHasInsect(1);
//                createLand.setCropId(6);
//                createLand.setIsWithered(0);
//            } else if (i == 7 || i == 8 || i == 9 || i == 10 || i == 11) {
//                createLand.setIsWithered(1);
//                createLand.setHasCrop(1);
//                createLand.setCropId(1);
//                createLand.setHasInsect(0);
//                createLand.setNowCropGrowStage(6);
//                createLand.setNextCropGrowStage(0);
//            } else {
//                createLand.setIsWithered(1);
//                createLand.setHasCrop(1);
//                createLand.setCropId(1);
//                createLand.setNowCropGrowStage(2);
//                createLand.setNextCropGrowStage(3);
//                createLand.setHasInsect(0);
//            }

            //几种土地
            if (i % (SystemCode.INITIATE_USER_LANDS / 4) == 0 && i != 0) {
                landTypeCode++;
            }
            createLand.setLandTypeCode(landTypeCode);
            createLand.setUsername(user.getUsername());
            landList.add(createLand);

        }
        return landRepository.save(landList);
    }

    /**
     * 初始化指定玩家的农场数据
     *
     * @param user 玩家信息
     * @return
     */
    @Override
    public List<FarmResponse> initiateFarmView(User user) {
        ArrayList<FarmResponse> farmResponsesList = new ArrayList<>();
        List<Land> landList = landRepository.findAllByUsername(user.getUsername());
        for (Land land : landList) {
//            //玩家种植了作物的土地
//            if (land.getHasCrop() != 0) {
//                FarmResponse farmResponse = new FarmResponse(land.getLandId(), cropGrowViewRepository.findByStageIdAndCropId(land.getNowCropGrowStage(), land.getCropId()));
//                farmResponsesList.add(farmResponse);
//            }
            //玩家拥有的所有土地信息
            FarmResponse farmResponse = new FarmResponse(SystemCode.FARM_RESPONSE_CODE_B, land,
                    cropGrowViewRepository.findByStageIdAndCropIdOrderByStageIdAsc(
                            land.getNowCropGrowStage(),
                            land.getCropId()));//升序查找
            farmResponsesList.add(farmResponse);
        }
        return farmResponsesList;
    }

    private void updateCropStage() {
        //查询种植了作物、不是成熟期和不是枯萎作物的土地
        List<LandView> landViewList = landViewRepository.findAllByHasCropAndIsWitheredAndIsMature(1, 0, 0);
        if (!landViewList.isEmpty()) {
            Date nowTime = new Date();
            for (LandView landView : landViewList) {
                //待更新对象
                Land landByFind = landRepository.findByLandId(landView.getLandId());
                //获取土地记录相关信息
                String username = landView.getUsername();//土地拥有者
                Date stateEndTime = landView.getStateEndTime();//本阶段所需生长时间
                int nowCropGrowStage = landByFind.getNowCropGrowStage();//当前阶段
                int cropId = landByFind.getCropId();//种子id
                CropGrow nowCropGrowStageByFind = cropGrowRepository.findByStageIdAndCropId(nowCropGrowStage, cropId);//当前阶段信息
                //本阶段生长时间大于生长结束时间
                if (nowTime.getTime() >= stateEndTime.getTime()) {
                    CropGrow nextCropGrowStageByFind = cropGrowRepository.findByStageIdAndCropId(nowCropGrowStage + 1, cropId);//下一生长阶段
                    landByFind.setGrowthTimeOfEachState(0);//生长时间归零
                    landByFind.setNowCropGrowStage(nowCropGrowStage + 1);//切换生长阶段，可能进入成熟期
                    landByFind.setStateEndTime(new Date(stateEndTime.getTime() + (nextCropGrowStageByFind.getGrowTime() * 1000L)));//记录下一阶段生长结束时间
                    //切换生长阶段时虫子仍未去除
                    if (landByFind.getHasInsect() == 1) {
                        landByFind.setHasInsect(0);
                        //TODO 如果产量为0时处理情况 否则该作物将减少1至2个果实
                        landByFind.setOutput(Math.max(landByFind.getOutput() - SystemCode.REDUCE_OUTPUT, 0));//减产
                    }
                }
                //记录进入成熟期的种子
                if (landByFind.getNowCropGrowStage() == SystemCode.CROP_GROW_STAGE_IS_MATURE) {
                    landByFind.setIsMature(1);
                    if (landByFind.getHasInsect() == 1) {
                        landByFind.setHasInsect(0);
                        //TODO 如果产量为0时处理情况 否则该作物将减少1至2个果实
                        landByFind.setOutput(Math.max(landByFind.getOutput() - SystemCode.REDUCE_OUTPUT, 0));//减产
                    }
                }
                //是否有概率生虫
                if (insectAlgorithm(landByFind, nowCropGrowStageByFind, nowTime)) {
                    landByFind.setHasInsect(1);//生虫
                }
                landByFind.setGrowthTimeOfEachState(nowCropGrowStageByFind.getGrowTime() + SystemCode.PER_TIME);//增加生长时间
                //保存数据
                landRepository.save(landByFind);
                //TODO 发送封装的消息，前端需要接受的参数landId
                FarmResponse farmResponse = new FarmResponse(SystemCode.FARM_RESPONSE_CODE_A, landByFind,
                        cropGrowViewRepository.findByStageIdAndCropIdOrderByStageIdAsc(landByFind.getNowCropGrowStage(),
                                landByFind.getCropId()), userRepository.findByUsername(landByFind.getUsername()));
                systemWebsocketHandler.sendMessageToOne(username, new TextMessage(JSONObject.fromObject(farmResponse).toString()));
            }
        }
    }
}
