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
 * @Description: 关键词action
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
     * 获取关键词列表方法
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
            //获取关键词集合
            List<Keyword> keywordList = keywordService.listKeyword(keywordBO);
            //封装结果集
            Result result = new Result(true);
            result.addDefaultModel("keywordList",keywordList);
            result.addDefaultModel("keywordBO",keywordBO);
            this.setResult(result);
            this.toVm(result);
        }catch (Exception e){
            log.error("【KeywordAction.list】封装result");
        }
        return SUCCESS;
    }

    /**
     * 将BO对象中时间进行转化
     */
    private void keywordBOToQuery() {
        KeywordQuery query = new KeywordQuery();
        query.setQueryBeginTime(DateUtil.format(keywordBO.getBeginTime(),"yyyy-MM-dd 00:00:00"));
        query.setQueryEndTime(DateUtil.format(keywordBO.getEndTime(),"yyyy-MM-dd 23:59:59"));
        keywordBO.setKeywordQuery(query);
    }


    /**
     * 关键词列表导出
     */
    public void download(){
        try {
            XExcel xExcel = XExcelBuilder.instance(KeywordExcelBO.class);
            xExcel.createHeard(createExcelHeader());
            //最多导出500条记录
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

            //设置表格名称
            StringBuffer fileNameStr = new StringBuffer();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            fileNameStr.append("关键词搜索频次TOP500-").append(sdf.format(new Date()));
            String fileName = new String(fileNameStr.toString().getBytes(),"ISO8859-1");
            //输出表格
            HttpServletResponse response = ServletActionContext.getResponse();
            OutputStream res = response.getOutputStream();
            //清空输出流
            response.reset();
            //设定输出文件头
            response.setHeader("Content-Disposition","attachment; filename=" + fileName + ".xls");
            response.setContentType("application/vnd.ms-excel");
            xExcel.write(res);
            res.flush();
            res.close();
        } catch (Exception e) {
            log.error("【KeywordAction.download】文件导出异常 :"+e.getMessage());
        }
    }

    /**
     * 生成excel表格头
     * @return
     */
    private List<String> createExcelHeader(){
        List<String> headerList = new ArrayList<String>();
        headerList.add("序号");
        headerList.add("关键词");
        headerList.add("次数");
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
