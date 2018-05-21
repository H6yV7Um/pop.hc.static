package com.jd.help.center.dao.helpsys;

import com.jd.help.center.domain.helpsys.HelpSYS;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-8-3
 * Time: 17:31:17
 * To change this template use File | Settings | File Templates.
 */
public interface HelpSYSDao {
    List<HelpSYS> findSYSAll();

    HelpSYS getSYSById(int id);

    int insertSYS(HelpSYS helpSYS);

    int updateSYS(HelpSYS helpSYS);

    int updateSYSStatus(HelpSYS helpSYS);

    boolean checkSYSstatus(int id);

    int getSYSByName(String name);
}
