package cn.jxufe.serivce.impl;

import cn.jxufe.bean.EasyUIData;
import cn.jxufe.entity.SeedBag;
import cn.jxufe.entity.view.SeedBagView;
import cn.jxufe.repository.SeedBagRepository;
import cn.jxufe.repository.view.SeedBagViewRepository;
import cn.jxufe.serivce.SeedBagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @create: 2022-05-26 09:46
 * @author: lwz
 * @description:
 **/
@Service
public class SeedBagImpl implements SeedBagService {
    @Autowired
    SeedBagRepository seedBagRepository;
    @Autowired
    SeedBagViewRepository seedBagViewRepository;

    @Override
    public EasyUIData<SeedBagView> findAllPageable(Pageable pageable) {
        Page<SeedBagView> page = seedBagViewRepository.findAll(pageable);
        EasyUIData<SeedBagView> easyUIData = new EasyUIData<SeedBagView>();
        easyUIData.setTotal(page.getTotalElements());
        easyUIData.setRows(page.getContent());
        return easyUIData;
    }

    @Override
    public List<SeedBagView> findAllByUsername(String username) {
        return seedBagViewRepository.findAllByUsername(username);
    }

    @Override
    public SeedBag save(SeedBag seedBag) {
        return seedBagRepository.save(seedBag);
    }

    @Override
    public void delete(SeedBag seedBag) {
        seedBagRepository.delete(seedBag);
    }
}
