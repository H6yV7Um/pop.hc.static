package com.jd.help.service.impl;

import com.jd.common.web.result.Result;
import com.jd.help.dao.SelfMenuDao;
import com.jd.help.domain.SelfMenu;
import com.jd.help.service.SelfMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * SelfMenu service ʵ��
 * generated by bud
 *
 * @author @laichendong
 */
@Service("selfMenuService")
public class SelfMenuServiceImpl implements SelfMenuService {
    @Resource
    private SelfMenuDao selfMenuDao;

    public Result list(SelfMenu selfMenu, int page, int pageSize) {
        Result result = new Result();
        if (selfMenu == null) {
            return result;
        }
//        PaginatedList<SelfMenu> selfMenuList = selfMenuDao.queryForList(selfMenu, page, pageSize);
//        result.setSuccess(true);
//        result.addDefaultModel("selfMenuList", selfMenuList);
        return result;
    }

    public Result detail(SelfMenu selfMenu) {
        Result result = new Result();
        if (selfMenu == null) {
            return result;
        }
        result.setSuccess(true);
        selfMenu = selfMenuDao.queryForObject(selfMenu);
        if (selfMenu == null) {
            return result;
        }
        result.addDefaultModel("selfMenu", selfMenu);

        return result;
    }

    public Result insert(SelfMenu selfMenu) {
        Result result = new Result();
        if (selfMenu == null) {
            result.setSuccess(false);
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
        Long id = selfMenuDao.insert(selfMenu);
        if (id <= 0) {
            result.setSuccess(false);
            result.setResultCode("system.insert.error");
        } else {
            result.setSuccess(true);
            result.setResultCode("system.insert.success");
        }
        return result;
    }

    public Result update(SelfMenu selfMenu) {
        Result result = new Result();
        if (selfMenu == null || selfMenu.getId() == null || selfMenu.getId() <= 0) {
            result.setSuccess(false);
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
//        boolean success = selfMenuDao.update(selfMenu);
//        result.setSuccess(success);
//        if(success){
//            result.setResultCode("system.update.success");
//        } else {
//            result.setResultCode("system.update.success");
//        }
        return result;
    }

    public Result delete(SelfMenu selfMenu) {
        Result result = new Result();
        if (selfMenu == null || selfMenu.getId() == null || selfMenu.getId() <= 0) {
            result.setSuccess(false);
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
//        boolean success = selfMenuDao.delete(selfMenu);
//        result.setSuccess(success);
//        if(success){
//            result.setResultCode("system.delete.success");
//        } else {
//            result.setResultCode("system.delete.error");
//        }
        return result;
    }
}
