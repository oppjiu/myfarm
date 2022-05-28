import cn.jxufe.entity.Land;
import cn.jxufe.entity.User;
import cn.jxufe.repository.LandRepository;
import cn.jxufe.repository.SeedBagRepository;
import cn.jxufe.repository.UserRepository;
import cn.jxufe.serivce.LandService;
import cn.jxufe.serivce.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @create: 2022-05-26 22:53
 * @author: lwz
 * @description:
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class TestOther {
    @Autowired
    UserRepository userRepository;
    @Autowired
    LandRepository landRepository;

    @Autowired
    SeedBagRepository seedBagRepository;

    @Autowired
    LandService userLandService;
    @Autowired
    UserService userService;

    /**
     * 初始化多个对象
     */
    @Test
    public void initiateObjectList() {
        ArrayList<Land> landList = new ArrayList<>();
        Collections.addAll(landList, new Land[10]);
        for (Land land : landList) {
            land = new Land();
            System.err.println("land = " + land);
        }
        System.err.println("landList.size() = " + landList.size());
    }

    /**
     * 生虫算法
     */
    @Test
    public void insectAlgorithm() {

    }

    @Test
    public void testCount() {
        System.err.println("landRepository.count() = " + landRepository.count());
    }

    @Test
    public void testDeleteInBatch() {
        Land userLand = new Land();
        userLand.setUsername("nihao");
        landRepository.deleteInBatch(landRepository.findAllByUsername(userLand.getUsername()));
    }

    /**
     * 测试用户注册功能
     */
    @Test
    public void testSaveUser() {
        User user = new User();
        user.setUsername("nihao");
        User result = userService.register(user);
        if (result != null) {
            System.err.println("userService.registerUser(user) = " + result);
        } else {
            System.err.println("重名");
        }
    }

    /**
     * 测试用户级联删除功能
     */
    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setUsername("nihao");

        userService.delete(user);
    }

    @Test
    public void testTemp() {
//        System.err.println("userService.findByUsername(\"nihao\") = " + userService.findByUsername("nihao"));
        System.err.println("userService.findByUsername(\"nihao\") = " + userRepository.findAll());
    }
}
