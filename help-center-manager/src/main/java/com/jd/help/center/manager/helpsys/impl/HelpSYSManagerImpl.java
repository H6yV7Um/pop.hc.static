package com.jd.help.center.manager.helpsys.impl;

import com.jd.help.center.dao.helpsys.HelpSYSDao;
import com.jd.help.center.domain.category.HelpCategory;
import com.jd.help.center.domain.helpsys.HelpSYS;
import com.jd.help.center.domain.helpsys.LocalHelpCategory;
import com.jd.help.center.domain.topic.HelpTopic;
import com.jd.help.center.manager.category.HelpCategoryManager;
import com.jd.help.center.manager.helpsys.HelpSYSManager;
import com.jd.help.center.manager.topic.HelpTopicManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-8-3
 * Time: 17:46:01
 * To change this template use File | Settings | File Templates.
 */
public class HelpSYSManagerImpl implements HelpSYSManager {
    private final static Log log = LogFactory.getLog(HelpSYSManagerImpl.class);
    private HelpSYSDao helpSYSDao;
    
    public void setHelpSYSDao(HelpSYSDao helpSYSDao) {
        this.helpSYSDao = helpSYSDao;
    }

    public List<HelpSYS> findSYSAll() {
        return this.helpSYSDao.findSYSAll();
    }

    public int insert(HelpSYS helpSYS) {
        return this.helpSYSDao.insertSYS(helpSYS);
    }

    public int updateSYS(HelpSYS helpSYS) {
        return this.helpSYSDao.updateSYS(helpSYS);
    }

    public int updateSYSStatus(HelpSYS helpSYS) {
        return this.helpSYSDao.updateSYSStatus(helpSYS);
    }

    public HelpSYS getSYSById(int id) {
        return this.helpSYSDao.getSYSById(id);
    }

    public boolean checkSYSstatus(int id) {
        return this.helpSYSDao.checkSYSstatus(id);
    }

    public int getSYSByName(String name) {
        return this.helpSYSDao.getSYSByName(name);
    }
}
