package com.jd.help.dao.lable.impl;

import com.jd.common.dao.BaseDao;
import com.jd.help.dao.lable.HelpLableMapper;
import com.jd.help.domain.helplable.HelpLable;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

/**
 * Created by zhaojianhong on 2018/3/27.
 */
@Repository("HelpLableMapper")
public class HelpLableMapperImpl extends BaseDao implements HelpLableMapper {
    /**
     * ע������Դ
     */
    @Override
    @Resource(name = "helpCenterDataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    public int deleteByPrimaryKey(HelpLable helpLable) {
        return delete("HelpLableMapper.deleteByPrimaryKey",helpLable);
    }

    public int insert(HelpLable helpLable) {
        return (Integer) insert("HelpLableMapper.insert",helpLable);
    }

    public HelpLable selectByPrimaryKey(HelpLable helpLable) {
        System.out.println("i'm coming");
        return (HelpLable) queryForObject("HelpLableMapper.selectByPrimaryKey", helpLable);
    }

    public int updateByPrimaryKey(HelpLable helpLable) {
        return 0;
    }

    public List<HelpLable> getHelpLableList(HelpLable helpLable) {
        return queryForList("HelpLableMapper.getHelpLableList", helpLable);
    }

    public List<HelpLable> getHelpLableListMust(HelpLable helpLable) {
        return queryForList("HelpLableMapper.getHelpLableListMust", helpLable);
    }

    @Override
    public List<HelpLable> nameRepeatCheck(HelpLable helpLable) {
        return queryForList("HelpLableMapper.nameRepeatCheck", helpLable);
    }
}
