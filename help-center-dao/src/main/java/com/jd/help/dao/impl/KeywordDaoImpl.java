package com.jd.help.dao.impl;

import com.jd.common.dao.BaseDao;
import com.jd.help.dao.KeywordDao;
import com.jd.help.domain.Keyword;
import com.jd.help.domain.KeywordBO;
import com.jd.ump.annotation.JProfiler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

/**
 * @author haoming1
 * @Description: 关键词持久层实现类
 * @Date Created in 20:47 2018/1/8
 * @Modified By:
 */
@Repository("KeywordDao")
public class KeywordDaoImpl extends BaseDao implements KeywordDao {

    private Log log = LogFactory.getLog(KeywordDaoImpl.class);

    /**
     * 注入数据源
     */
    @Override
    @Resource(name = "helpCenterDataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Override
    public void insert(Keyword keyword) {
        try {
            insert("Keyword.insert",keyword);
        }catch (Exception e){
            log.error("【KeywordDaoImpl.insert】插入关键字失败 :"+e.getMessage());
        }
    }

    @Override
    public List<Keyword> listKeyword(KeywordBO bo) {
        List list = null;
        try {
            list = queryForList("Keyword.queryForList",bo);
        }catch (Exception e){
            log.error("【KeywordDaoImpl.listKeyword】查询关键词列表失败 :"+e.getMessage());
        }
        return list;
    }

    @Override
    public Long countTodayKeyword(KeywordBO bo) {
        Long count = null;
        try {
            count = (Long) queryForObject("Keyword.countTodayKeyword",bo);
        }catch (Exception e){
            log.error("【KeywordDaoImpl.countTodayKeyword】查询今日关键词总数失败 :"+e.getMessage());
        }
        return count;
    }


}
