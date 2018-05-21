package com.jd.help.admin.web.test;

import com.alibaba.fastjson.JSONObject;
import com.jd.help.HelpBaseAction;
import com.jd.help.enums.IssueOptypesEnum;
import com.jd.help.enums.OndemandCourseOpTypeEnum;
import com.jd.help.enums.SceneOpTypeEnum;
import com.jd.help.jmq.UpdateCourseMessage;
import com.jd.help.jmq.UpdateIssueMessage;
import com.jd.help.jmq.UpdateSceneMessage;
import com.jd.help.jmq.listener.UpdateCoursesMessageListener;
import com.jd.help.jmq.listener.UpdateIssueMessageListener;
import com.jd.help.jmq.listener.UpdateSceneMessageListener;
import com.jd.jmq.common.message.Message;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class TestAction extends HelpBaseAction {

    private static Logger logger = LogManager.getLogger(TestAction.class);

    @Autowired
    private UpdateIssueMessageListener updateIssueMessageListener;

    @Autowired
    private UpdateCoursesMessageListener updateCoursesMessageListener;

    @Autowired
    private UpdateSceneMessageListener updateSceneMessageListener;

    public void ti(){

        //add knowledge db and es issue
        Message m1 = new Message();
        UpdateIssueMessage um1 = new UpdateIssueMessage();
        um1.setOpType(IssueOptypesEnum.ADD.getCode());
        um1.setIssueId(1810l);
        m1.setText(JSONObject.toJSONString(um1));

        //update knowledge db and es issue
        Message m2 = new Message();
        UpdateIssueMessage um2 = new UpdateIssueMessage();
        um2.setOpType(IssueOptypesEnum.ADD.getCode());
        um2.setIssueId(992l);
        m2.setText(JSONObject.toJSONString(um2));

        //del knowledge db and es issue
        Message m3 = new Message();
        UpdateIssueMessage um3 = new UpdateIssueMessage();
        um3.setOpType(IssueOptypesEnum.DEL.getCode());
        um3.setIssueId(1798l);
        m3.setText(JSONObject.toJSONString(um3));

        List<Message> mList = new ArrayList<Message>();
        mList.add(m1);
        mList.add(m2);
        mList.add(m3);
        try {
            updateIssueMessageListener.onMessage(mList);
        } catch (Exception e) {
            logger.error("t1 error",e);
        }
    }

    public void tx(){

        //add knowledge db and es course
        Message m1 = new Message();
        UpdateCourseMessage um1 = new UpdateCourseMessage();
        um1.setOpType(OndemandCourseOpTypeEnum.COUNT.getCode());
        um1.setCourseId(16725850l);
        um1.setCourseType(2);
        m1.setText(JSONObject.toJSONString(um1));

        //update knowledge db and es course
//        Message m2 = new Message();
//        UpdateCourseMessage um2 = new UpdateCourseMessage();
//        um2.setOpType(OndemandCourseOpTypeEnum.ADD.getCode());
//        um2.setCourseId(16725860l);
//        um2.setCourseType(2);
//        m2.setText(JSONObject.toJSONString(um2));

        //del knowledge db and es issue
//        Message m3 = new Message();
//        UpdateCourseMessage um3 = new UpdateCourseMessage();
//        um3.setOpType(OndemandCourseOpTypeEnum.DEL.getCode());
//        um3.setCourseId(16725893l);
//        um3.setCourseType(2);
//        m3.setText(JSONObject.toJSONString(um3));

        List<Message> mList = new ArrayList<Message>();
        mList.add(m1);
//        mList.add(m2);
//        mList.add(m3);
        try {
            updateCoursesMessageListener.onMessage(mList);
        } catch (Exception e) {
            logger.error("t1 error",e);
        }
    }

    public void ts(){

        //add knowledge db scene
        Message m1 = new Message();
        UpdateSceneMessage um1 = new UpdateSceneMessage();
        um1.setOpType(SceneOpTypeEnum.ADDDB.getCode());
        um1.setSceneId(256l);
        m1.setText(JSONObject.toJSONString(um1));

        //update knowledge db scene
        Message m2 = new Message();
        UpdateSceneMessage um2 = new UpdateSceneMessage();
        um2.setOpType(SceneOpTypeEnum.ADDDB.getCode());
        um2.setSceneId(512l);
        m2.setText(JSONObject.toJSONString(um2));

        //add knowledge db and es scene
        Message m3 = new Message();
        UpdateSceneMessage um3 = new UpdateSceneMessage();
        um3.setOpType(SceneOpTypeEnum.ADDDBES.getCode());
        um3.setSceneId(257l);
        m3.setText(JSONObject.toJSONString(um3));

        //update knowledge db and es scene
        Message m4 = new Message();
        UpdateSceneMessage um4 = new UpdateSceneMessage();
        um4.setOpType(SceneOpTypeEnum.ADDDBES.getCode());
        um4.setSceneId(513l);
        m4.setText(JSONObject.toJSONString(um4));

        //del knowledge db scene
        Message m5 = new Message();
        UpdateSceneMessage um5 = new UpdateSceneMessage();
        um5.setOpType(SceneOpTypeEnum.DELDB.getCode());
        um5.setSceneId(258l);
        m5.setText(JSONObject.toJSONString(um5));

        //del knowledge db and es scene
        Message m6 = new Message();
        UpdateSceneMessage um6 = new UpdateSceneMessage();
        um6.setOpType(SceneOpTypeEnum.DELDBES.getCode());
        um6.setSceneId(514l);
        m6.setText(JSONObject.toJSONString(um6));

        List<Message> mList = new ArrayList<Message>();
        mList.add(m1);
        mList.add(m2);
        mList.add(m3);
        mList.add(m4);
        mList.add(m5);
        mList.add(m6);
        try {
            updateSceneMessageListener.onMessage(mList);
        } catch (Exception e) {
            logger.error("t1 error",e);
        }
    }


    public static void main(String[] args) {
        String s = "http://172.20.145.85/cors.php?http://172.18.139.138:20200/?key=1&pagesize=100&page=1&enc_url_gbk=yes";
        try {
            URL url = new URL(s);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            if(sb != null){
                String result = URLDecoder.decode(sb.toString(),"GBK");
                System.out.println(result);
            }
            br.close();
            connection.disconnect();
            System.out.println(sb.toString());
        } catch (Exception e) {
            logger.error("main error",e);
        }
    }



}
