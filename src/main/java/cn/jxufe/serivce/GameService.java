package cn.jxufe.serivce;

import cn.jxufe.entity.Land;
import cn.jxufe.entity.User;

import java.util.List;

/**
 * （1）支持多玩家模式
 * 多玩家是指用户可以通过模拟登录切换当前玩家，并对当前玩家的农场进行播种、除虫、收获、除枯叶等操作。 多玩家模式不需要支持偷菜等功能。
 * （2）玩家在农场中进行的播种、除虫、收获、除枯叶等操作应该以不同的操作光标进行区别。
 * （3）服务器每间隔2秒对各个用户的农田进行管控，包括：生长阶段切换、让作物出现虫害，并向用户发送状态变更消息，要求客户UI进行积极响应。
 * （4）服务器负责对用户操作进行响应，并向用户发送状态变更消息，要求客户UI进行积极响应（包括音效）。用户操作包括：
 * 播种：public Message actionPlant(int landId, int cId, HttpSession session)
 * 除虫：public Message actionKillWorm(int landId, HttpSession session)
 * 收获：public Message actionHarvest(int landId, HttpSession session)
 * 除枯草：public Message actionCleanLand(int landId, HttpSession session)
 * <p>
 * （5）农场事件描述
 * （a）播种：用户是否合法？土地是否合法？试图种植的种子是否合法？种袋中是否有足够的种子？土地类型与种子自身要求是否一致？全部条件在后台验证通过后，用户种袋中的相应种子数-1，用户该块土地数据更新（或添加），并向用户发送状态变更消息，要求客户UI进行积极响应，更新特定土地上的表达信息。
 * （b）除虫：用户是否合法？土地是否合法？是否真的有虫害？全部条件在后台验证通过后，对生虫数据进行清理。用户收获经验+2、金币+1和积分+2。然后向用户发送状态变更消息，要求客户UI进行积极响应，更新特定土地上的表达信息。
 * （c）收获：用户是否合法？土地是否合法？全部条件在后台验证通过后，该块土地变为“枯草”状态，用户收获相应的经验、金币和积分。然后向用户发送状态变更消息，要求客户UI进行积极响应，更新特定土地上的表达信息。
 * （d）除枯草：用户是否合法？土地是否合法？全部条件在后台验证通过后，对作物进行多季判断。未生长至最后一季前，在操作完“除枯草”后，该块土地自动转入种子状态（该变化不额外消耗种袋中的种子）。长至最后一季，在操作完“除枯草”后，该块土地自动转入空白土地状态，等待再次手工播种。“除枯草”用户收获经验+5、积分+5。
 * （e）生虫：在作物进入到新的生长阶段时按照该生长阶段概率设置作物是否生虫，然后向用户发送状态变更消息，要求客户UI进行积极响应，更新特定土地上的表达信息。当作物在该阶段生虫后，如果用户及时除虫，则不会影响作物最后产量，否则作物总体产量在该阶段随机减产1到2个果实。在作物进行新的生长阶段时重复“生虫”算法。
 * <p>
 * （6）服务器段农田管控服务随Tomcat启动自动运行，无需用户干预。
 * （7）作物各生长阶段耗时见T_CropsGrow的growTime字段，计时单位为秒。
 * （8）作物在各生长阶段生虫概率见T_CropsGrow的insect字段，如：0.2。
 * （9）在出现负面的消息时（message.code<0）,应该同时播放“嘲讽”音效。
 * （10）在出现农田长虫时,应该同时播放提示音效。
 *
 * @create: 2022-05-25 09:55
 * @author: lwz
 * @description:
 **/
public interface GameService {
    /*服务器自动执行*/
    void gameSeversInitiate();

    boolean insectAlgorithm();

    void updateCropStage();

    List<Land> initiateUserLands(int landNumber, User user);
}