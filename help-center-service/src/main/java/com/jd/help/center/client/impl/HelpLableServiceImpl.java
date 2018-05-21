package com.jd.help.center.client.impl;

import com.alibaba.fastjson.JSONObject;
import
        com.jd.help.center.client.service.HelpLableService;
import com.jd.help.dao.lable.HelpLableMapper;
import com.jd.help.domain.helplable.HelpLable;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaojianhong on 2018/3/30.
 */
@Component("helpLableService")
public class HelpLableServiceImpl implements
        HelpLableService {
    private Logger logger = LoggerFactory.getLogger
            (HelpLableServiceImpl.class);
    private final String SUCCESS = "success";
    private final String FAILD = "faild";


    @Resource
    private HelpLableMapper helpLableMapper;

    /**
     * 对外提供必选标签接口(学习中心,规则中心)
     *
     * @return
     */
    public String getHelpLableList() {
        CallerInfo info = Profiler.registerInfo("vender_help_center.HelpLableServiceImpl.getHelpLableList"," vender_help_center",false, true);
        try {
            HelpLable helpLable = new HelpLable();
            Map<String, Object> jsonData = new
                    HashMap<String, Object>();
            jsonData.put("token", FAILD);
            System.out.println(helpLable.getLevel() ==
                    null);
            helpLable.setLevel(1);
            List<HelpLable> firstLevelLableList =
                    helpLableMapper.getHelpLableListMust(helpLable);
            for (HelpLable firstLevelLable :
                    firstLevelLableList) {
                HelpLable queryLable = new HelpLable();
                queryLable.setParentId
                        (firstLevelLable.getId());
                List<HelpLable> secondLevelLableList =
                        helpLableMapper.getHelpLableListMust(queryLable);
                firstLevelLable.setChildList
                        (secondLevelLableList);
            }
            jsonData.put("token", SUCCESS);
            jsonData.put("data", firstLevelLableList);
            logger.info("vender_help_center.HelpLableServiceImpl.getHelpLableL.jsonData={}",jsonData);

            Profiler.registerInfoEnd(info);
            return JSONObject.toJSONString(jsonData);
        } catch (Exception e) {
            Profiler.functionError(info);
            logger.info("vender_help_center.HelpLableServiceImpl.getHelpLableList",e);
            logger.info("vender_help_center.HelpLableServiceImpl.getHelpLableList has error = {}",e);
        }
        Profiler.registerInfoEnd(info);
        return null;
    }
}
