package com.jd.help.admin.web.lable;

import com.alibaba.fastjson.JSONObject;
import com.jd.help.HelpBaseAction;
import com.jd.help.center.web.util.WebHelper;
import com.jd.help.dao.lable.HelpLableMapper;
import com.jd.help.domain.helplable.HelpLable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaojianhong on 2018/3/26.
 */
public class LableAction extends HelpBaseAction {
    private Logger logger = LoggerFactory.getLogger(LableAction.class);
    @Resource
    private HelpLableMapper helpLableMapper;

    private Map<String, Object> jsonData = new HashMap<String, Object>();

    private HelpLable helpLable;

    public String testjsf() {
        jsonData.put("token", "test");
        HelpLable helpLable = new HelpLable();
        helpLable.setId(1);
        helpLable = helpLableMapper.selectByPrimaryKey(helpLable);
        System.out.println(JSONObject.toJSONString(helpLable));
        jsonData.put("helpLable", helpLable);
        helpLable.setName("第一");
        List<HelpLable> list = helpLableMapper.getHelpLableList(helpLable);
        return "json";
    }

    public String helpLable() {
        return SUCCESS;
    }

    public String getHelpLableList() {
        try {
            logger.info("getHelpLableList.helpLable = {}",JSONObject.toJSONString(helpLable));
            jsonData.put("token", ERROR);
            List<HelpLable> resultList = new ArrayList<HelpLable>();
            if (helpLable.getLevel() != 0) {
                resultList = helpLableMapper.getHelpLableList(helpLable);
            } else {
                helpLable.setLevel(1);
                List<HelpLable> firstLevelLableList = helpLableMapper.getHelpLableList(helpLable);
                if(firstLevelLableList.size() > 0){
                    for (HelpLable firstLevelLable : firstLevelLableList) {
                        resultList.add(firstLevelLable);
                        HelpLable queryLable = new HelpLable();
                        queryLable.setParentId(firstLevelLable.getId());
                        List<HelpLable> secondLevelLableList = helpLableMapper.getHelpLableList(queryLable);
                        for (HelpLable secondLevelLable : secondLevelLableList) {
                            resultList.add(secondLevelLable);
                        }
                    }
                }else{
                    helpLable.setLevel(2);
                    resultList = helpLableMapper.getHelpLableList(helpLable);
                }

            }
            jsonData.put("resultList", resultList);
        } catch (Exception e) {
            logger.info("getHelpLableList.e = {}",JSONObject.toJSONString(helpLable));
        }
        jsonData.put("token", SUCCESS);

        return "json";
    }

    public String insertLable() {
        try {
            jsonData.put("token", ERROR);
            // TODO 本地注掉
            helpLable.setCreatedPin(WebHelper.getPin());
            helpLable.setModifiedPin(WebHelper.getPin());
            logger.info("LableAction.insertLable.helpLable ={}",JSONObject.toJSONString(helpLable));
            helpLableMapper.insert(helpLable);
            jsonData.put("token", SUCCESS);
        } catch (Exception e) {
            logger.info("LableAction.insertLable.exception ={}",e);
            logger.info("");
        }
        return "json";
    }


    public String deleteLable() {
        try {
            jsonData.put("token", ERROR);
            helpLableMapper.deleteByPrimaryKey(helpLable);
        } catch (Exception e) {
            logger.info("");
        }
        jsonData.put("token", SUCCESS);
        return "json";
    }

    public String getFirstLevelLable() {
        try {
            jsonData.put("token", ERROR);
            helpLable.setLevel(1);
            List<HelpLable> resultList = new ArrayList<HelpLable>();
            helpLable.setLevel(1);
            resultList =  helpLableMapper.getHelpLableList(helpLable);
            jsonData.put("data", resultList);
        } catch (Exception e) {
            logger.info("");
        }
        jsonData.put("token", SUCCESS);

        return "json";
    }

    public String getHelpLableListTest() {
        try {
            HelpLable helpLable = new HelpLable();
            jsonData.put("token",ERROR);
            System.out.println(helpLable.getLevel() == null);
            helpLable.setLevel(1);
            List<HelpLable> firstLevelLableList = helpLableMapper.getHelpLableList(helpLable);
            for (HelpLable firstLevelLable : firstLevelLableList) {
                HelpLable queryLable = new HelpLable();
                queryLable.setParentId(firstLevelLable.getId());
                List<HelpLable> secondLevelLableList = helpLableMapper.getHelpLableList(queryLable);
                firstLevelLable.setChildList(secondLevelLableList);
            }
            jsonData.put("token",SUCCESS);
            jsonData.put("data",firstLevelLableList);
            return "json";
        } catch (Exception e) {
            logger.info("");
        }
        return "json";
    }

    public String nameRepeatCheck() {
        try {
            jsonData.put("token", SUCCESS);
            List<HelpLable>  list = helpLableMapper.nameRepeatCheck(helpLable);
            if(list.size() > 0){
                jsonData.put("token", ERROR);
            }
        } catch (Exception e) {
            logger.info("");
        }
        return "json";
    }

    public String getAllLableList(){
        try {
            HelpLable helpLable = new HelpLable();
            jsonData.put("token",ERROR);
            System.out.println(helpLable.getLevel() == null);
            helpLable.setLevel(1);
            List<HelpLable> firstLevelLableList = helpLableMapper.getHelpLableList(helpLable);
            for (HelpLable firstLevelLable : firstLevelLableList) {
                HelpLable queryLable = new HelpLable();
                queryLable.setParentId(firstLevelLable.getId());
                List<HelpLable> secondLevelLableList = helpLableMapper.getHelpLableList(queryLable);
                firstLevelLable.setChildList(secondLevelLableList);
            }
            jsonData.put("token",SUCCESS);
            jsonData.put("data",firstLevelLableList);
        } catch (Exception e) {
            logger.info("");
        }
        return "json";
    }

    public Map<String, Object> getJsonData() {
        return jsonData;
    }

    public void setJsonData(Map<String, Object> jsonData) {
        this.jsonData = jsonData;
    }

    public HelpLable getHelpLable() {
        return helpLable;
    }

    public void setHelpLable(HelpLable helpLable) {
        this.helpLable = helpLable;
    }
}
