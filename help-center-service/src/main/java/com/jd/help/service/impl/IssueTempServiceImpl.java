package com.jd.help.service.impl;

import com.jd.common.web.result.Result;
import com.jd.help.dao.IssueTempDao;
import com.jd.help.domain.IssueTemp;
import com.jd.help.service.IssueTempService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * IssueTemp service ʵ��
 * generated by bud
 *
 * @author @laichendong
 */
@Service("issueTempService")
public class IssueTempServiceImpl implements IssueTempService {
    @Resource
    private IssueTempDao issueTempDao;

    public Result list(IssueTemp issueTemp, int page, int pageSize) {
        Result result = new Result();
        if (issueTemp == null) {
            return result;
        }
//        PaginatedList<IssueTemp> issueTempList = issueTempDao.queryForList(issueTemp, page, pageSize);
//        result.setSuccess(true);
//        result.addDefaultModel("issueTempList", issueTempList);
        return result;
    }

    public Result detail(IssueTemp issueTemp) {
        Result result = new Result();
        if (issueTemp == null) {
            return result;
        }
        result.setSuccess(true);
        issueTemp = issueTempDao.queryForObject(issueTemp);
        if (issueTemp == null) {
            return result;
        }
        result.addDefaultModel("issueTemp", issueTemp);

        return result;
    }

    public Result insert(IssueTemp issueTemp) {
        Result result = new Result();
        if (issueTemp == null) {
            result.setSuccess(false);
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
        Long id = issueTempDao.insert(issueTemp);
        if (id <= 0) {
            result.setSuccess(false);
            result.setResultCode("system.insert.error");
        } else {
            result.setSuccess(true);
            result.setResultCode("system.insert.success");
        }
        return result;
    }

    public Result update(IssueTemp issueTemp) {
        Result result = new Result();
//        if (issueTemp == null || issueTemp.getId() == null || issueTemp.getId() <= 0) {
//            result.setSuccess(false);
//            result.setResultCode("system.no.entity.to.operate");
//            return result;
//        }
//        boolean success = issueTempDao.update(issueTemp);
//        result.setSuccess(success);
//        if(success){
//            result.setResultCode("system.update.success");
//        } else {
//            result.setResultCode("system.update.success");
//        }
        return result;
    }

    public Result delete(IssueTemp issueTemp) {
        Result result = new Result();
//        if (issueTemp == null || issueTemp.getId() == null || issueTemp.getId() <= 0) {
//            result.setSuccess(false);
//            result.setResultCode("system.no.entity.to.operate");
//            return result;
//        }
//        boolean success = issueTempDao.delete(issueTemp);
//        result.setSuccess(success);
//        if(success){
//            result.setResultCode("system.delete.success");
//        } else {
//            result.setResultCode("system.delete.error");
//        }
        return result;
    }
}