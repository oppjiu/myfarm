package cn.jxufe.serivce.impl;

import cn.jxufe.serivce.GameService;
import cn.jxufe.websocket.SystemWebsocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.util.Date;
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

    }

    @Override
    public void serverActionChangeCropStage() {

    }

    @Override
    public void serverActionCreateInsect() {

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
