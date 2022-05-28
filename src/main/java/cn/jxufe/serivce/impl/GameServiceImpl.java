package cn.jxufe.serivce.impl;

import cn.jxufe.entity.view.LandView;
import cn.jxufe.repository.view.LandViewRepository;
import cn.jxufe.serivce.GameService;
import cn.jxufe.websocket.SystemWebsocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

    @Override
    public void gameSeversInitiate() {
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        talkToAll("现在服务器时间是：" + new Date());
                    }
                }, 0, 5000);
    }

    @Override
    public void insectAlgorithm() {
        //查询种植了作物和不是枯萎作物的土地
        List<LandView> landViewList = landViewRepository.findAllByHasCropAndIsWithered(1, 0);
        if (!landViewList.isEmpty()) {
            Date nowTime = new Date();
            for (LandView landView : landViewList) {
                //生长时间大于生长结束时间
                Date stateEndTime = landView.getStateEndTime();
                if (nowTime.getTime() >= stateEndTime.getTime()) {

                } else {

                }
            }
        }
    }

    @Override
    public void updateCropStage() {

    }

    @Override
    public void serverActionPlantSeed() {

    }

    @Override
    public void serverActionKillWorm() {

    }

    @Override
    public void serverActionHarvest() {

    }

    @Override
    public void serverActionCleanGrass() {

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
