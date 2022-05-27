package cn.jxufe.utils;

import cn.jxufe.bean.EasyUIDataPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @create: 2022-05-11 08:59
 * @author: lwz
 * @description:
 **/
public class EasyUIUtils {
    /**
     * EasyUI前端分页请求处理
     *
     * @param pageRequest EasyUIDataPageRequest封装数据
     * @return Pageable
     */
    public static Pageable requestProcess(EasyUIDataPageRequest pageRequest) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (pageRequest.getOrder().equals("asc")) {
            orders.add(new Sort.Order(Sort.Direction.ASC, pageRequest.getSort()));
        } else {
            orders.add(new Sort.Order(Sort.Direction.DESC, pageRequest.getSort()));
        }
        return new PageRequest(pageRequest.getPage() - 1, pageRequest.getRows(), new Sort(orders));
    }
}
