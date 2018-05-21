package com.jd.help.admin.web.publicportal;

import com.jd.common.util.PaginatedList;
import com.jd.common.util.StringUtils;
import com.jd.common.util.base.PaginatedArrayList;
import com.jd.common.web.result.Result;
import com.jd.fastjson.JSONObject;
import com.jd.help.HelpBaseAction;
import com.jd.help.center.web.util.WebHelper;
import com.jd.help.dao.publicportal.HelpMenuLabelMapper;
import com.jd.help.domain.publicportal.HelpMenuLabel;
import com.jd.help.jimdb.JimdbConstants;
import com.jd.help.service.PublicPortalService;
import com.jd.jim.cli.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaojianhong on 2018/4/17.
 */
public class HelpMenuLableAction extends HelpBaseAction {
    private final Logger logger = LoggerFactory.getLogger(HelpMenuLableAction.class);
    private HelpMenuLabel helpMenuLabel;

    private Map<String, Object> jsonData = new HashMap<String, Object>();

    @Resource
    private HelpMenuLabelMapper helpMenuLabelMapper;

    @Resource
    private PublicPortalService publicPortalService;

    @Resource(name = "jimClient")
    private Cluster jimClient;

    public String testInsert() {
        try {
            jsonData.put("token", "faild");
            publicPortalService.insert(helpMenuLabel);
        } catch (Exception e) {
            logger.info("vender_help_center.HelpMenuLableAction.testInsert has error", e);
        } finally {
        }
        jsonData.put("token", "success");
        return "json";
    }

    public String insert() {
        try {
            jsonData.put("token", "faild");
            helpMenuLabel.setCreatedPin(WebHelper.getPin());
            helpMenuLabelMapper.insert(helpMenuLabel);
/*
            //淇濆瓨椤甸潰鐭ヨ瘑鍏崇郴鑷崇紦瀛?
*/
            publicPortalService.insert(helpMenuLabel);
            jimClient.set(JimdbConstants.KEY_PREFIX_ONE_LABLE + helpMenuLabel.getMenuId(), JSONObject.toJSONString(helpMenuLabel));
        } catch (Exception e) {
            logger.info("vender_help_center.HelpMenuLableAction.insert has error", e);
        } finally {
        }
        jsonData.put("token", "success");
        return "json";
    }

    public String update() {
        try {
            jsonData.put("token", "faild");
            helpMenuLabel.setModifiedPin(WebHelper.getPin());
            helpMenuLabelMapper.updateByMenuId(helpMenuLabel);
            jimClient.set(JimdbConstants.KEY_PREFIX_ONE_LABLE + helpMenuLabel.getMenuId(), JSONObject.toJSONString(helpMenuLabel));
        } catch (Exception e) {
            logger.info("vender_help_center.HelpMenuLableAction.update has error", e);
        } finally {
        }
        jsonData.put("token", "success");
        return "json";
    }

    public String delete() {
        try {
            jsonData.put("token", "faild");
            // helpMenuLabel.setModifiedPin(WebHelper.getPin());
            helpMenuLabelMapper.deleteByMenuId(helpMenuLabel);
            jimClient.set(JimdbConstants.KEY_PREFIX_ONE_LABLE + helpMenuLabel.getMenuId(), "");
        } catch (Exception e) {
            logger.info("vender_help_center.HelpMenuLableAction.delete has error", e);
        } finally {
        }
        jsonData.put("token", "success");
        return "json";
    }

    public String getHelpMenuLable() {
        try {
            jsonData.put("token", "faild");
            // helpMenuLabel.setModifiedPin(WebHelper.getPin());
            HelpMenuLabel data = helpMenuLabelMapper.selectByMenuId(helpMenuLabel);
            jsonData.put("data", data);
        } catch (Exception e) {
            logger.info("vender_help_center.HelpMenuLableAction.getHelpMenuLable has error", e);
        } finally {
        }
        jsonData.put("token", "success");
        return "json";
    }

    public String getHelpMenuLableList() {
        try {
            if (helpMenuLabel == null) {
                helpMenuLabel = new HelpMenuLabel();
            } else {
                if (StringUtils.isNotEmpty(helpMenuLabel.getLabelNames())) {
                    helpMenuLabel.setLabelNameList(Arrays.asList(helpMenuLabel.getLabelNames().split(" ")));
                }
            }
            Result result = new Result(true);
            page = (page <= 0 ? 1 : page);
            PaginatedList<HelpMenuLabel> pageList = new PaginatedArrayList<HelpMenuLabel>();
            List<HelpMenuLabel> list = helpMenuLabelMapper.getHelpMenuLableList(helpMenuLabel);
            int total = list.size();
            if (total > 0) {
                pageList.setIndex(page);
                pageList.setPageSize(5); //璁剧疆pageSize
                pageList.setTotalItem(total);
                int totalPage = (pageList.getTotalItem() % pageList.getPageSize() == 0) ? pageList.getTotalItem() / pageList.getPageSize() : pageList.getTotalItem() / pageList.getPageSize() + 1;
                if (page < totalPage) {
                    for (int i = (page - 1) * pageList.getPageSize(); i < page * pageList.getPageSize(); i++) {
                        pageList.add(list.get(i));
                    }
                } else {
                    for (int i = (page - 1) * pageList.getPageSize(); i < pageList.getTotalItem(); i++) {
                        pageList.add(list.get(i));
                    }
                }
            }
            for (HelpMenuLabel bean : list) {
                String[] arr1 = bean.getLabel1Names() == null ? new String[0] : bean.getLabel1Names().split(" ");
                String[] arr2 = bean.getLabelNames() == null ? new String[0] : bean.getLabelNames().split(" ");
                String name = "";
                if (arr1.length == arr2.length) {
                    for (int n = 0; n < arr1.length; n++) {
                        name = name + arr1[n] + "->" + arr2[n] + " ";
                    }
                } else {
                    logger.info("该页面与标签对应关系异常 menuId={}", bean.getMenuId());
                }
                bean.setLabel1Names(name);
            }
            result.addDefaultModel("pageList", pageList);
            toVm(result);
        } catch (Exception e) {
            logger.info("vender_help_center.HelpMenuLableAction.getHelpMenuLableList has error", e);
        } finally {
        }
        return SUCCESS;
    }

    public String menuIdRepeatCheck() {
        try {
            jsonData.put("token", SUCCESS);
            List<HelpMenuLabel> list = helpMenuLabelMapper.nameRepeatCheck(helpMenuLabel);
            if (list.size() > 0) {
                jsonData.put("token", ERROR);
            }
        } catch (Exception e) {
            logger.info("vender_help_center.HelpMenuLableAction.menuIdRepeatCheck has error", e);
        }
        return "json";
    }

    public Map<String, Object> getJsonData() {
        return jsonData;
    }

    public void setJsonData(Map<String, Object> jsonData) {
        this.jsonData = jsonData;
    }

    public HelpMenuLabel getHelpMenuLabel() {
        return helpMenuLabel;
    }

    public void setHelpMenuLabel(HelpMenuLabel helpMenuLabel) {
        this.helpMenuLabel = helpMenuLabel;
    }
}
