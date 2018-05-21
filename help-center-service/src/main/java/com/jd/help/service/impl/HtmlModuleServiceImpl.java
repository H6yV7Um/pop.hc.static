package com.jd.help.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jd.common.util.PaginatedList;
import com.jd.common.util.base.PaginatedArrayList;
import com.jd.common.web.result.Result;
import com.jd.help.dao.HtmlModuleDao;
import com.jd.help.domain.HtmlModule;
import com.jd.help.service.HtmlModuleService;

@Service("htmlModuleService")
public class HtmlModuleServiceImpl implements HtmlModuleService{

	@Resource
    private HtmlModuleDao htmlModuleDao;
	
	@Override
	public Result list(HtmlModule htmlModule, int page, int pageSize) {
		Result result = new Result();
        result.setSuccess(true);
        result.addDefaultModel("htmlModuleList", htmlModuleDao.queryForList(htmlModule, page, pageSize));
        return result;
	}

	@Override
	public Result detail(HtmlModule htmlModule) {
		Result result = new Result();
        if (htmlModule == null) {
            return result;
        }
        result.setSuccess(true);
        htmlModule = htmlModuleDao.queryForObject(htmlModule);
        if (htmlModule == null) {
            return result;
        }
        result.addDefaultModel("htmlModule", htmlModule);

        return result;
	}

	@Override
	public Result insert(HtmlModule htmlModule) {
		Result result = new Result();
        if (htmlModule == null) {
            result.setSuccess(false);
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
        Long id = htmlModuleDao.insert(htmlModule);
        if (id <= 0) {
            result.setSuccess(false);
            result.setResultCode("system.insert.error");
        } else {
            result.setSuccess(true);
            result.setResultCode("system.insert.success");
        }
        return result;
	}

	@Override
	public Result update(HtmlModule htmlModule) {
		Result result = new Result();
        if (htmlModule == null || htmlModule.getId() == null) {
            result.setSuccess(false);
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
        boolean success = (htmlModuleDao.update(htmlModule) == 1);
        result.setSuccess(success);
        if(success){
            result.setResultCode("system.update.success");
        } else {
            result.setResultCode("system.update.success");
        }
        return result;
	}

	@Override
	public Result delete(HtmlModule htmlModule) {
		Result result = new Result();
        if (htmlModule == null || htmlModule.getId() == null) {
            result.setSuccess(false);
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
        boolean success = (htmlModuleDao.delete(htmlModule) == 1);
        result.setSuccess(success);
        if(success){
            result.setResultCode("system.update.success");
        } else {
            result.setResultCode("system.update.success");
        }
        return result;
	}

    @Override
    public List<HtmlModule> findByKeies(String[] moduleNames,int siteId) {
        return htmlModuleDao.findByKeies(moduleNames,siteId);
    }
}
