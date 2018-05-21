package com.jd.help.center.service.timetask.impl;

import com.jd.common.metrics.param.MetricsInfoQuery;
import com.jd.common.metrics.service.MetricsSummaryService;
import com.jd.help.center.domain.constants.MetricsConstants;
import com.jd.help.center.domain.constants.UMPConstants;
import com.jd.help.center.manager.category.LocalHelpCategoryManager;
import com.jd.help.center.service.timetask.SYSTimeTaskService;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-11-30
 * Time: 17:05:12
 * To change this template use File | Settings | File Templates.
 */
public class SYSTimeTaskServiceImpl implements SYSTimeTaskService {
    private Log log= LogFactory.getLog(SYSTimeTaskServiceImpl.class);
    private LocalHelpCategoryManager localHelpCategoryManager;

    public void setLocalHelpCategoryManager(LocalHelpCategoryManager localHelpCategoryManager) {
        this.localHelpCategoryManager = localHelpCategoryManager;
    }

    public void initLocalSYS() throws Exception{
        log.info("initLocalSYS start----------->");
        long start = System.currentTimeMillis();
        localHelpCategoryManager.initLocalHelpCategory();
        log.info("initLocalSYS end--------->end,Elapsed time:" + (System.currentTimeMillis() - start) + " milliseconds");
    }
}
