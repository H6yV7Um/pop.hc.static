package com.jd.help.center.service.helpsys;

import com.jd.common.web.result.Result;
import com.jd.help.center.domain.helpsys.HelpSYS;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-8-3
 * Time: 17:48:32
 * To change this template use File | Settings | File Templates.
 */
public interface HelpSYSService {
    Result findSYSAll();
    Result getSYSById(int id);
    Result updatSYS(HelpSYS helpSYS);
    Result updateSYSStatus(HelpSYS helpSYS);
    Result insertSYS(HelpSYS helpSYS);

    void initCategory();
}
