import cn.jxufe.entity.Land;
import cn.jxufe.entity.User;
import cn.jxufe.repository.CropRepository;
import cn.jxufe.repository.LandRepository;
import cn.jxufe.repository.UserRepository;
import cn.jxufe.repository.view.CropGrowViewRepository;
import cn.jxufe.serivce.SeedBagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * @create: 2022-05-27 21:58
 * @author: lwz
 * @description:
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class TestTemp {
    @Autowired
    SeedBagService seedBagService;
    @Autowired
    LandRepository landRepository;

    @Test
    public void temp01() throws InterruptedException {
        Date date = new Date(new Date().getTime() + 10000);
        System.err.println("date = " + date);
        while (!date.toString().equals(new Date().toString())) {
            Thread.sleep(2000);
            System.err.println("new Date() = " + new Date());
        }
    }

    @Test
    public void temp02() {

        java.util.Date javaDate = new java.util.Date();
        long javaTime = javaDate.getTime();
        System.err.println("The Java Date is: " + javaDate.toString());

        java.sql.Date sqlDate = new java.sql.Date(javaTime);
        System.err.println("The SQL DATE is: " + sqlDate.toString());

        java.sql.Time sqlTime = new java.sql.Time(javaTime);
        System.err.println("The SQL TIME is: " + sqlTime.toString());

        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(javaTime);
        System.err.println("The SQL TIMESTAMP is: " + sqlTimestamp.toString());
    }

    @Test
    public void temp03() throws InterruptedException {
        List<Land> landList = landRepository.findAll();
        System.err.println("landList = " + landList);


        Land land = landList.get(0);
        Date stateEndTime = land.getStateEndTime();
        System.err.println("stateEndTime = " + stateEndTime);
        while (true) {
            Thread.sleep(1000);
            Date date = new Date();
            if (date.getTime() >= stateEndTime.getTime()) {
                System.err.println("超时");
                break;
            }
        }
    }

    @Autowired
    CropGrowViewRepository cropGrowViewRepository;
    @Autowired
    CropRepository cropRepository;
    @Autowired
    UserRepository userRepository;
    @Test
    public void testTemp() {
//        System.err.println("userService.findByUsername(\"nihao\") = " + userService.findByUsername("nihao"));
//        CropGrowView cropGrowViewByFind = cropGrowViewRepository.findByStageIdAndCropId(1, 1);
//        System.out.println("cropGrowViewByFind = " + cropGrowViewByFind);
//        for (CropGrowView cropGrowView : cropGrowViewRepository.findAll()) {
//            System.out.println("cropGrowView = " + cropGrowView);
//        }
//        for (Crop crop : cropRepository.findAll()) {
//            System.out.println("crop = " + crop);
//        }

//        System.out.println("landRepository.maxIdIndex() = " + landRepository.getMaxLandIdIndex());
        User allByNickname = userRepository.findAllByNickname("123");
        System.out.println("allByNickname = " + allByNickname);
    }
}
