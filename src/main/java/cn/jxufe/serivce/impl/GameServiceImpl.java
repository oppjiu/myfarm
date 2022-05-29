package cn.jxufe.serivce.impl;

import cn.jxufe.bean.SystemCode;
import cn.jxufe.entity.CropGrow;
import cn.jxufe.entity.Land;
import cn.jxufe.entity.User;
import cn.jxufe.entity.view.CropGrowView;
import cn.jxufe.entity.view.LandView;
import cn.jxufe.repository.CropGrowRepository;
import cn.jxufe.repository.LandRepository;
import cn.jxufe.repository.view.CropGrowViewRepository;
import cn.jxufe.repository.view.LandViewRepository;
import cn.jxufe.serivce.GameService;
import cn.jxufe.websocket.SystemWebsocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

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

    @Override
    public void gameSeversInitiate() {
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        talkToAll("现在服务器时间是：" + new Date());
                        //更新游戏数据
//                        updateCropStage();
                    }
                }, 0, SystemCode.PER_TIME);
    }

    public boolean insectAlgorithm() {
        //TODO 当玩家在线时才生虫
        //TODO 不能在即将进入下一阶段的前一段时间内生虫
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
        ArrayList<Land> landList = new ArrayList<>();
        for (int i = 0; i < landNumber; i++) {
            Land createLand = new Land();
            createLand.setUsername(user.getUsername());
            landList.add(createLand);
        }
        return landRepository.save(landList);
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
                    //生长时间归零
                    landByFind.setGrowthTimeOfEachState(0);
                    //切换生长阶段，可能进入成熟期
                    landByFind.setNowCropGrowStage(nowCropGrowStage + 1);
                    //记录下一阶段生长结束时间
                    landByFind.setStateEndTime(new Date(stateEndTime.getTime() + (nextCropGrowStageByFind.getGrowTime() * 1000L)));
                    //切换生长阶段时虫子仍未去除
                    if (landByFind.getHasInsect() == 1) {
                        landByFind.setHasInsect(0);
                        //TODO 如果产量为0时处理情况 否则该作物将减少1至2个果实
                        landByFind.setOutput(Math.max(landByFind.getOutput() - SystemCode.REDUCE_OUTPUT, 0));//减产
                    }
                }
                //记录进入成熟期的种子
                if (nowCropGrowStageByFind.getCropStateCode() == SystemCode.CROP_GROW_STAGE_IS_MATURE) {
                    landByFind.setIsMature(1);
                }
                //是否有概率生虫
                if (insectAlgorithm()) {
                    landByFind.setHasInsect(1);//生虫
                }
                //增加生长时间
                landByFind.setGrowthTimeOfEachState(nowCropGrowStageByFind.getGrowTime() + SystemCode.PER_TIME);
                //保存数据
                landRepository.save(landByFind);
                //TODO 发送封装的消息，前端需要接受的参数landId
                CropGrowView cropGrowViewFindById = cropGrowViewRepository.findByStageIdAndCropId(landByFind.getNowCropGrowStage(), landByFind.getCropId());

                systemWebsocketHandler.sendMessageToOne(username, new TextMessage(cropGrowViewFindById.toString()));
            }
        }
    }

    private void talkToAll(String message) {
        //TODO
        systemWebsocketHandler.sendMessageToAll(new TextMessage(message));
    }

    private void talkToOne(String username, String message) {
        //TODO
        systemWebsocketHandler.sendMessageToOne(username, new TextMessage(message));
    }
}
