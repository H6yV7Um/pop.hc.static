package com.jd.help.center.manager.helpsys;

import com.jd.help.center.domain.helpsys.HelpSYS;
import com.jd.help.center.domain.helpsys.LocalHelpCategory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-8-3
 * Time: 17:43:58
 * To change this template use File | Settings | File Templates.
 */
public interface HelpSYSManager {

    List<HelpSYS> findSYSAll();
    int insert(HelpSYS helpSYS);
    int updateSYS(HelpSYS helpSYS);
    int updateSYSStatus(HelpSYS helpSYS);
    HelpSYS getSYSById(int id);
    boolean checkSYSstatus(int id);
    int getSYSByName(String name);
}
