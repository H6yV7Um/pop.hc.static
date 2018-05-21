package com.jd.help.service.impl;

import com.jd.help.dao.KeywordDao;
import com.jd.help.domain.Keyword;
import com.jd.help.domain.KeywordBO;
import com.jd.help.domain.KeywordQuery;
import com.jd.help.service.KeywordService;
import com.jd.mongodbclient.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author haoming1
 * @Description: ¹Ø¼ü´ÊÒµÎñ²ã
 * @Date Created in 20:36 2018/1/9
 * @Modified By:
 */
@Service("KeywordService")
public class KeywordServiceImpl implements KeywordService{

    @Resource
    private KeywordDao keywordDao;

    @Override
    public List<Keyword> listKeyword(KeywordBO bo) {
        return keywordDao.listKeyword(bo);
    }

    @Override
    public Long countTodayKeyword() {
        String beginTime = DateUtil.format(new Date(),"yyyy-MM-dd 00:00:00");
        String endTime = DateUtil.format(new Date(),"yyyy-MM-dd 23:59:59");
        KeywordBO bo = new KeywordBO();
        KeywordQuery query = new KeywordQuery();
        query.setQueryBeginTime(beginTime);
        query.setQueryEndTime(endTime);
        bo.setKeywordQuery(query);
        return keywordDao.countTodayKeyword(bo);
    }
}
