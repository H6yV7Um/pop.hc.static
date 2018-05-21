package com.jd.help.admin.web.keyword;

import com.jd.common.web.result.Result;
import com.jd.help.HelpBaseAction;
import com.jd.help.admin.web.common.NoHrmPrivilegeException;
import com.jd.help.domain.*;
import com.jd.help.excel.XExcel;
import com.jd.help.excel.XExcelBuilder;
import com.jd.help.service.KeywordService;
import com.jd.mongodbclient.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author haoming1
 * @Description: �ؼ���action
 * @Date Created in 15:07 2018/1/9
 * @Modified By:
 */
public class KeywordAction extends HelpBaseAction {

    private Log log = LogFactory.getLog(KeywordAction.class);

    private KeywordBO keywordBO = new KeywordBO();

    @Resource
    private KeywordService keywordService;

    private Result result;
    /**
     * ��ȡ�ؼ����б���
     * @return
     */
    public String list() throws NoHrmPrivilegeException {
        if(null==keywordBO.getBeginTime()||null == keywordBO.getEndTime()){
            Result result = new Result(true);
            result.addDefaultModel("firstSearch",-1);
            this.setResult(result);
            this.toVm(result);
            return SUCCESS;
        }
        keywordBOToQuery();
        keywordBO.setTopNum(50);
        try {
            initSite(false);
            //��ȡ�ؼ��ʼ���
            List<Keyword> keywordList = keywordService.listKeyword(keywordBO);
            //��װ�����
            Result result = new Result(true);
            result.addDefaultModel("keywordList",keywordList);
            result.addDefaultModel("keywordBO",keywordBO);
            this.setResult(result);
            this.toVm(result);
        }catch (Exception e){
            log.error("��KeywordAction.list����װresult");
        }
        return SUCCESS;
    }

    /**
     * ��BO������ʱ�����ת��
     */
    private void keywordBOToQuery() {
        KeywordQuery query = new KeywordQuery();
        query.setQueryBeginTime(DateUtil.format(keywordBO.getBeginTime(),"yyyy-MM-dd 00:00:00"));
        query.setQueryEndTime(DateUtil.format(keywordBO.getEndTime(),"yyyy-MM-dd 23:59:59"));
        keywordBO.setKeywordQuery(query);
    }


    /**
     * �ؼ����б���
     */
    public void download(){
        try {
            XExcel xExcel = XExcelBuilder.instance(KeywordExcelBO.class);
            xExcel.createHeard(createExcelHeader());
            //��ർ��500����¼
            keywordBOToQuery();
            keywordBO.setTopNum(500);
            List<Keyword> keywordList = keywordService.listKeyword(keywordBO);
            KeywordExcelBO excelBO = new KeywordExcelBO();
            for (int i = 0; i < keywordList.size(); i++) {
                excelBO.setId(i+1);
                excelBO.setKeyword(keywordList.get(i).getKeyword());
                excelBO.setTotal(keywordList.get(i).getTotal());
                xExcel.add(excelBO);
            }

            //���ñ������
            StringBuffer fileNameStr = new StringBuffer();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            fileNameStr.append("�ؼ�������Ƶ��TOP500-").append(sdf.format(new Date()));
            String fileName = new String(fileNameStr.toString().getBytes(),"ISO8859-1");
            //������
            HttpServletResponse response = ServletActionContext.getResponse();
            OutputStream res = response.getOutputStream();
            //��������
            response.reset();
            //�趨����ļ�ͷ
            response.setHeader("Content-Disposition","attachment; filename=" + fileName + ".xls");
            response.setContentType("application/vnd.ms-excel");
            xExcel.write(res);
            res.flush();
            res.close();
        } catch (Exception e) {
            log.error("��KeywordAction.download���ļ������쳣 :"+e.getMessage());
        }
    }

    /**
     * ����excel���ͷ
     * @return
     */
    private List<String> createExcelHeader(){
        List<String> headerList = new ArrayList<String>();
        headerList.add("���");
        headerList.add("�ؼ���");
        headerList.add("����");
        return headerList;
    }

    public KeywordBO getKeywordBO() {
        return keywordBO;
    }

    public void setKeywordBO(KeywordBO keywordBO) {
        this.keywordBO = keywordBO;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
