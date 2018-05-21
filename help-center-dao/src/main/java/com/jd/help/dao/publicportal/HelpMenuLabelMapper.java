package com.jd.help.dao.publicportal;

import com.jd.help.domain.publicportal.HelpMenuLabel;

import java.util.List;

public interface HelpMenuLabelMapper {
    int deleteByMenuId(HelpMenuLabel record);

    int insert(HelpMenuLabel record);

    HelpMenuLabel selectByMenuId(HelpMenuLabel record);

    int updateByMenuId(HelpMenuLabel record);

    List<HelpMenuLabel> getHelpMenuLableList(HelpMenuLabel helpMenuLabel);

    List<HelpMenuLabel> nameRepeatCheck(HelpMenuLabel helpMenuLabel);

    List<HelpMenuLabel> validCheck(HelpMenuLabel record);

}