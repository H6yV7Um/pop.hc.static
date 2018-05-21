package com.jd.help.dao.publicportal;

import com.jd.help.domain.publicportal.HelpMenuKnowledge;

public interface HelpMenuKnowledgeMapper {
    int deleteByMenuId(HelpMenuKnowledge record);

    int insert(HelpMenuKnowledge record);

    HelpMenuKnowledge selectByMenuId(HelpMenuKnowledge record);

    int updateByMenuId(HelpMenuKnowledge record);
}