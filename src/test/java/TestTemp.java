import cn.jxufe.entity.SeedBag;
import cn.jxufe.serivce.SeedBagService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @create: 2022-05-27 21:58
 * @author: lwz
 * @description:
 **/
public class TestTemp {
    @Autowired
    SeedBagService seedBagService;
    @Test
    public void temp01() {
        Date stateEndTime = new Date();
        System.out.println("stateEndTime = " + stateEndTime);
    }@Test
    public void temp02() {
        SeedBag seedBag = new SeedBag();
        seedBag.setSeedNumber(10);
        seedBagService.delete(seedBag);
    }
}
