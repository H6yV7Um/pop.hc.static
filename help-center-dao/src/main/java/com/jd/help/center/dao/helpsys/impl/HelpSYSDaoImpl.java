package com.jd.help.center.dao.helpsys.impl;

import com.jd.common.dao.BaseDao;
import com.jd.help.center.dao.helpsys.HelpSYSDao;
import com.jd.help.center.domain.helpsys.HelpSYS;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-8-3
 * Time: 17:35:57
 * To change this template use File | Settings | File Templates.
 */
public class HelpSYSDaoImpl extends BaseDao implements HelpSYSDao {
    public List<HelpSYS> findSYSAll() {
        return queryForList("HelpSYS.findSYSAll");
    }

    public HelpSYS getSYSById(int id) {
        return (HelpSYS) queryForObject("HelpSYS.getSYSById", id);
    }

    public int insertSYS(HelpSYS helpSYS) {
        try {
            return Integer.parseInt(String.valueOf(insert("HelpSYS.createHelpSYS", helpSYS)));
        } catch (DataAccessException e) {
            log.error("Method:insertSYS----->" + e.getMessage());
            return 0;
        }
    }

    public int updateSYS(HelpSYS helpSYS) {
        return update("HelpSYS.updateSYS", helpSYS);
    }

    public int updateSYSStatus(HelpSYS helpSYS) {
        return update("HelpSYS.updateSYSStatus", helpSYS);
    }

    public boolean checkSYSstatus(int id) {
        try {
            int i = (Integer) queryForObject("HelpSYS.checkSYSstatus", id);
            if (i > 0) {
                return true;
            }
        } catch (DataAccessException e) {
            log.error("Method:checkSYSstatus----->" + e.getMessage());
            return false;
        }
        return false;
    }

    public int getSYSByName(String name) {
        try {
            List<HelpSYS> list = this.queryForList("HelpSYS.findSYSByName", name);
            if (list.size() > 0) {
                return list.iterator().next().getId();
            }
        } catch (DataAccessException e) {
            log.error("Method:findSYSByName------>" + e.getMessage());
            return 0;
        }
        return 0;
    }
}
