package cn.jxufe.serivce.impl;

import cn.jxufe.entity.Land;
import cn.jxufe.entity.view.LandView;
import cn.jxufe.repository.LandRepository;
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
    LandViewRepository landViewRepository;

    @Override
    public List<LandView> findAll() {
        return landViewRepository.findAll();
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
