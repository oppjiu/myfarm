package cn.jxufe.serivce.impl;

import cn.jxufe.entity.Land;
import cn.jxufe.entity.User;
import cn.jxufe.entity.view.LandView;
import cn.jxufe.repository.LandRepository;
import cn.jxufe.repository.UserRepository;
import cn.jxufe.repository.view.LandViewRepository;
import cn.jxufe.serivce.LandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @create: 2022-05-26 22:33
 * @author: lwz
 * @description:
 **/
@Service
public class LandServiceImpl implements LandService {
    @Autowired
    LandRepository landRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    LandViewRepository landViewRepository;

    @Override
    public List<LandView> findAll() {
        //TODO UserLandViewRepository 视图
//        return userLandViewRepository.findAll();
        return null;
    }


    @Override
    public Land save(Land land) {
        return landRepository.save(land);
    }

    @Override
    public void delete(Land land) {
        landRepository.delete(land);
    }
}
