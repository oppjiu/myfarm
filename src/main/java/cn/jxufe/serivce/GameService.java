package cn.jxufe.serivce;

import cn.jxufe.entity.Land;
import cn.jxufe.entity.User;

import java.util.List;

/**
 * @create: 2022-05-25 09:55
 * @author: lwz
 * @description:
 **/
public interface GameService {
    /*服务器自动执行*/
    void gameSeversInitiate();

    List<Land> initiateUserLands(int landNumber, User user);
}