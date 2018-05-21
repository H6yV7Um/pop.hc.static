package com.jd.help.service.impl;

import com.jd.help.dao.IssueAnswerDao;
import com.jd.help.dao.IssueDao;
import com.jd.help.domain.IssueQuery;
import com.jd.help.es.search.IssueEsIndexer;
import com.jd.help.service.BackdoorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author haoming1
 * @Description: 后门用service
 * @Date Created in 17:09 2018/1/26
 * @Modified By:
 */
@Service("BackdoorService")
public class BackdoorServiceImpl implements BackdoorService{

    @Resource
    private IssueDao issueDao;
    @Resource
    private IssueEsIndexer indexer;

    //将mysql数据导入es
    @Override
    public void mysql2Es() {
        IssueQuery issueQuery = new IssueQuery();
        issueQuery.setSiteId(8);
        List<Integer> integers = issueDao.query4Issue2Es(issueQuery);
        for (Integer integer : integers) {
            indexer.indexOne(integer);
        }
    }
}
