package com.jd.help.dao.publicportal.impl;

import com.jd.common.dao.BaseDao;
import com.jd.help.dao.publicportal.HelpMenuKnowledgeMapper;
import com.jd.help.domain.publicportal.HelpMenuKnowledge;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by zhaojianhong on 2018/4/21.
 */
@Repository("HelpMenuKnowledgeMapper")
public class HelpMenuKnowledgeMapperImpl extends BaseDao implements HelpMenuKnowledgeMapper {
    @Resource(name = "helpCenterDataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }
    @Override
    public int deleteByMenuId(HelpMenuKnowledge record) {
        return delete("helpMenuKnowledgeMapper.deleteByMenuId",record);
    }

    @Override
    public int insert(HelpMenuKnowledge record) {
        return (Integer) insert("helpMenuKnowledgeMapper.insert",record);
    }

    @Override
    public HelpMenuKnowledge selectByMenuId(HelpMenuKnowledge record) {
        return (HelpMenuKnowledge) queryForObject("helpMenuKnowledgeMapper.selectByMenuId",record);
    }

    @Override
    public int updateByMenuId(HelpMenuKnowledge record) {
        return update("helpMenuKnowledgeMapper.updateByMenuId",record);
    }
}
