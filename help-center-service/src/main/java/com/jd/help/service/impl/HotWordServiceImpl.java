package com.jd.help.service.impl;

import com.jd.common.web.result.Result;
import com.jd.help.dao.HotWordDao;
import com.jd.help.domain.HotWord;
import com.jd.help.service.HotWordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * HotWord service ʵ��
 * generated by bud
 *
 * @author @laichendong
 */
@Service("hotWordService")
public class HotWordServiceImpl implements HotWordService {
    @Resource
    private HotWordDao hotWordDao;

    public Result list(HotWord hotWord, int page, int pageSize) {
        Result result = new Result();
        if (hotWord == null) {
            return result;
        }
//        PaginatedList<HotWord> hotWordList = hotWordDao.queryForList(hotWord, page, pageSize);
//        result.setSuccess(true);
//        result.addDefaultModel("hotWordList", hotWordList);
        return result;
    }

    public Result detail(HotWord hotWord) {
        Result result = new Result();
        if (hotWord == null) {
            return result;
        }
        result.setSuccess(true);
        hotWord = hotWordDao.queryForObject(hotWord);
        if (hotWord == null) {
            return result;
        }
        result.addDefaultModel("hotWord", hotWord);

        return result;
    }

    public Result insert(HotWord hotWord) {
        Result result = new Result();
        if (hotWord == null) {
            result.setSuccess(false);
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
        Long id = hotWordDao.insert(hotWord);
        if (id <= 0) {
            result.setSuccess(false);
            result.setResultCode("system.insert.error");
        } else {
            result.setSuccess(true);
            result.setResultCode("system.insert.success");
        }
        return result;
    }

    public Result update(HotWord hotWord) {
        Result result = new Result();
        if (hotWord == null || hotWord.getId() == null || hotWord.getId() <= 0) {
            result.setSuccess(false);
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
//        boolean success = hotWordDao.update(hotWord);
//        result.setSuccess(success);
//        if(success){
//            result.setResultCode("system.update.success");
//        } else {
//            result.setResultCode("system.update.success");
//        }
        return result;
    }

    public Result delete(HotWord hotWord) {
        Result result = new Result();
        if (hotWord == null || hotWord.getId() == null || hotWord.getId() <= 0) {
            result.setSuccess(false);
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
//        boolean success = hotWordDao.delete(hotWord);
//        result.setSuccess(success);
//        if(success){
//            result.setResultCode("system.delete.success");
//        } else {
//            result.setResultCode("system.delete.error");
//        }
        return result;
    }
}
