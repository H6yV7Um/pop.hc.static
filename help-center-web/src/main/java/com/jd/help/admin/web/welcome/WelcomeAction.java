
package com.jd.help.admin.web.welcome;

import com.jd.help.HelpBaseAction;
import com.jd.help.center.web.util.WebHelper;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaojianhong on 2018/4/12.
 */
public class WelcomeAction extends HelpBaseAction {
    private final Logger logger = LoggerFactory.getLogger(WelcomeAction.class);

    private Map<String, Object> jsonData = new HashMap<String, Object>();

    public String welcome() {
        return SUCCESS;
    }

    public String getKingName() {
        try {
            jsonData.put("token","faild");
             jsonData.put("kingName", WebHelper.getPin());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        jsonData.put("token","success");
        return  "json";
    }

    public Map<String, Object> getJsonData() {
        return jsonData;
    }

    public void setJsonData(Map<String, Object> jsonData) {
        this.jsonData = jsonData;
    }



}
