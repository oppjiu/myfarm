package cn.jxufe.bean;

import java.io.File;

/**
 * @create: 2022-05-28 16:57
 * @author: lwz
 * @description:
 **/
public class SystemCode {
    //游戏数据更新间隔时间
    public final static int PER_TIME = 2;
    //初始化玩家土地数量
    public final static int INITIATE_USER_LANDS = 24;
    //减产数量
    public final static int REDUCE_OUTPUT = 2;
    //作物生长阶段
    public final static int CROP_GROW_STAGE_IS_MATURE = 3;
    public final static int CROP_GROW_STAGE_IS_WITHERED = 4;
    //除虫获得的经验、积分和金币
    public final static int KILL_WORM_GAIN_EXP = 2;
    public final static int KILL_WORM_GAIN_POINT = 1;
    public final static int KILL_WORM_GAIN_MONEY = 2;
    //除草获得的经验、积分和金币
    public final static int CLEAN_GRASS_GAIN_EXP = 2;
    public final static int CLEAN_GRASS_GAIN_POINT = 2;
    public final static int CLEAN_GRASS_GAIN_MONEY = 0;
    //默认的用户路头像路径
    public final static String DEFAULT_USER_IMAGE_URL = "ext/images/headImages/0.jpg";
    //头像存储路径
    public final static String DEFAULT_HEAD_IMAGE_SAVE_PATH = File.separator + "ext" + File.separator + "images" + File.separator + "headImages";
    //用户session名称
    public final static String USER_SESSION_NAME = "curUser";
    //webSocketSession用户数据存储名称
    public final static String WEBSOCKET_SESSION_NAME = "user";
    public final static String LOGIN_SESSION_NAME = "userLogin";

}
