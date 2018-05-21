package com.jd.help.center.manager.category;

import com.jd.help.center.domain.border.BorderInfo;
import com.jd.help.center.domain.category.HelpCategory;
import com.jd.help.center.domain.helpsys.HelpSYS;
import com.jd.help.center.domain.helpsys.LocalHelpCategory;
import com.jd.help.center.domain.topic.HelpTopic;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-12-1
 * Time: 16:39:59
 * To change this template use File | Settings | File Templates.
 */
public interface LocalHelpCategoryManager {

    LocalHelpCategory initLocalHelpCategory();

    List<HelpSYS> getAllHelpSYSList();

    List<HelpTopic> getHelpTopicByCategory(int categoryId);

    List<HelpCategory> getHelpCategoryBySysId(int sysId);

    HelpSYS getHelpSysByName(String name);

    HelpTopic getHelpTopicByTopicId(int topicId);

    HelpCategory getHelpCategoryByCategoryId(int categoryId);

    HelpSYS getHelpSYSById(int id);

    Map<Integer, List<HelpTopic>> getTopicListMap();

    Map<Integer, List<HelpCategory>> getCategoryListMap();

    Map<Integer, HelpCategory> getCategoryMap();

    Map<Integer, HelpSYS> getSysMap();

    Map<Integer, HelpTopic> getTopicMap();

    Map<String,BorderInfo> getBorderInfoMap();

    List<BorderInfo> getBorderInfoList();
}
