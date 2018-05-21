package com.jd.help.admin.web.issue;

import com.jd.help.HelpBaseAction;
import com.jd.help.center.domain.constants.ImageConstants;
import com.jd.image.common.ImageUpload;
import com.jd.image.model.Message;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UploadImageAction extends HelpBaseAction {

    private String UPLOAD_SUCCESS = "1";

    private File upfile;

    private HashMap<String, String> imgUploadPath;
    
    private Map<String,Object> jsonRoot = new HashMap<String,Object>();
    
    /**
     * ueditor ·µ»Ø²ÎÊý
     */
    private String state = "SUCCESS";
    private String url = "";
    private String title = "";
    private String original = "";

    public String upload() {
        String imageKey = (String) imgUploadPath.get(ImageConstants.HELP_CENTER_IMAGE_KEY);
        String basePath = (String) imgUploadPath.get(ImageConstants.HELP_CENTER_IMAGE_HOST);
        String path = null;
        try {
            path = ImageUpload.uploadFile(upfile, imageKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotBlank(path)) {
            JSONArray array = JSONArray.fromObject(path);
            JSONObject jsonObject = (JSONObject) array.iterator().next();
            Message message = (Message) JSONObject.toBean(jsonObject, Message.class);
            if (message != null && UPLOAD_SUCCESS.equals(message.getId())) {
                url = basePath + message.getMsg();
            }
        }
        jsonRoot.put("state", state);
        jsonRoot.put("url", url);
        return "jsonResult";
    }

    public File getUpfile() {
        return upfile;
    }

    public void setUpfile(File upfile) {
        this.upfile = upfile;
    }

    public HashMap<String, String> getImgUploadPath() {
        return imgUploadPath;
    }

    public void setImgUploadPath(HashMap<String, String> imgUploadPath) {
        this.imgUploadPath = imgUploadPath;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

	public Map<String, Object> getJsonRoot() {
		return jsonRoot;
	}


}
