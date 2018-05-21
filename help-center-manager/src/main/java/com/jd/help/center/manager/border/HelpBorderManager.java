package com.jd.help.center.manager.border;

import com.jd.help.center.domain.border.BorderInfo;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 12-10-10
 * Time: ÉÏÎç10:57
 * To change this template use File | Settings | File Templates.
 */
public interface HelpBorderManager {

    public List<BorderInfo> getAllBorderInfo();

    public List<BorderInfo> getBorderInfoByType(String type);

    public boolean insertOrUpdateBorderInfo(BorderInfo borderInfo);

    public boolean insertBorderInfo(BorderInfo borderInfo);

    public boolean updateBorderInfo(BorderInfo borderInfo);

    public BorderInfo getBorderInfoByKey(String key);

    public boolean deleteBorder(String key);

    public BorderInfo getBorderInfoFrontByKey(String key);

}
