package com.jd.help.dao.publicportal.impl;

import com.jd.common.dao.BaseDao;
import com.jd.help.dao.publicportal.HelpMenuLabelMapper;
import com.jd.help.domain.publicportal.HelpMenuLabel;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

/**
 * Created by zhaojianhong on 2018/4/12.
 */
@Repository("HelpMenuLabelMapper")
public class HelpMenuLabelMapperImpl extends BaseDao implements HelpMenuLabelMapper {
    @Resource(name = "helpCenterDataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Override
    public int deleteByMenuId(HelpMenuLabel record) {
        return delete("helpMenuLableMapper.deleteByMenuId", record);
    }

    @Override
    public int insert(HelpMenuLabel record) {
        return (Integer) insert("helpMenuLableMapper.insert", record);
    }

    @Override
    public HelpMenuLabel selectByMenuId(HelpMenuLabel record) {
        return (HelpMenuLabel) queryForObject("helpMenuLableMapper.selectByMenuId", record);
    }

    @Override
    public int updateByMenuId(HelpMenuLabel record) {
        return  update("helpMenuLableMapper.updateByMenuId", record);
    }

    @Override
    public List<HelpMenuLabel> getHelpMenuLableList(HelpMenuLabel helpMenuLabel) {
        return queryForList("helpMenuLableMapper.getHelpMenuLableList",helpMenuLabel);
    }

    @Override
    public List<HelpMenuLabel> nameRepeatCheck(HelpMenuLabel helpMenuLabel) {
        return queryForList("helpMenuLableMapper.nameRepeatCheck", helpMenuLabel);
    }

    @Override
    public List<HelpMenuLabel> validCheck(HelpMenuLabel helpMenuLabel) {
        return queryForList("helpMenuLableMapper.validCheck", helpMenuLabel);
    }

}
