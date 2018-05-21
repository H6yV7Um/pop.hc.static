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
 * @Description: �ؼ��ʳ־ò�ʵ����
 * @Date Created in 20:47 2018/1/8
 * @Modified By:
 */
@Repository("KeywordDao")
public class KeywordDaoImpl extends BaseDao implements KeywordDao {

    private Log log = LogFactory.getLog(KeywordDaoImpl.class);

    /**
     * ע������Դ
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
            log.error("��KeywordDaoImpl.insert������ؼ���ʧ�� :"+e.getMessage());
        }
    }

    @Override
    public List<Keyword> listKeyword(KeywordBO bo) {
        List list = null;
        try {
            list = queryForList("Keyword.queryForList",bo);
        }catch (Exception e){
            log.error("��KeywordDaoImpl.listKeyword����ѯ�ؼ����б�ʧ�� :"+e.getMessage());
        }
        return list;
    }

    @Override
    public Long countTodayKeyword(KeywordBO bo) {
        Long count = null;
        try {
            count = (Long) queryForObject("Keyword.countTodayKeyword",bo);
        }catch (Exception e){
            log.error("��KeywordDaoImpl.countTodayKeyword����ѯ���չؼ�������ʧ�� :"+e.getMessage());
        }
        return count;
    }


}
