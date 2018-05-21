package com.jd.help.dao.lable;


import com.jd.help.domain.helplable.HelpLable;

import java.util.List;

public interface HelpLableMapper {
    int deleteByPrimaryKey(HelpLable helpLable);

    int insert(HelpLable helpLable);

    HelpLable selectByPrimaryKey(HelpLable helpLable);

    int updateByPrimaryKey(HelpLable helpLable);

    List<HelpLable> getHelpLableList(HelpLable helpLable);

    List<HelpLable> getHelpLableListMust(HelpLable helpLable);

    List<HelpLable> nameRepeatCheck(HelpLable helpLable);
}