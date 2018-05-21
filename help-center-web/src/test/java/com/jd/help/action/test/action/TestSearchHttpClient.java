package com.jd.help.action.test.action;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.jd.help.domain.knowledge.KnowledgeBean;
import com.jd.help.domain.knowledge.KnowledgeContentTypeBean;
import com.jd.help.domain.knowledge.KnowledgeLabelBean;
import com.jd.help.enums.KnowledgeContentTypesEnum;
import com.jd.help.enums.KnowledgeSearchSortEnum;
import com.jd.help.utils.HttpClientUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yfxialiang on 2018/5/15.
 */
public class TestSearchHttpClient {

    private static Logger logger = LogManager.getLogger(TestSearchHttpClient.class);

    private static int DEFAULT_PAGE_SIZE = 100;

    public static void main(String[] args) {
        TestSearchHttpClient client = new TestSearchHttpClient();
        try {
            Integer page = 1;
            Integer pageSize = 100;
            String key = "京东";
            String bizIds = "4065 15555";
            String bizTypeIds = "2";
            String contentTypes = "1 2 3";
            String label1Ids = "95";
            String label2Ids = "96 97";
            Integer sort = 1;
            client.getRemoteKnowledge(page,pageSize,key,null,bizTypeIds,
                    contentTypes,label1Ids,label2Ids,sort);

//            client.joinFiltType(null,null,null,null,null);
        } catch (Exception e) {
            logger.error("getRemoteKnowledge error",e);
        }
    }

    /**
     *
     * @param key 搜索关键词
     * @param page 页码
     * @param pageSize 页大小
     * @param bizTypeIds knowledge系统来源ID，多个以空格分隔
     * @param label1Ids 一级标签IDs，以空格分隔
     * @param label2Ids 一级标签IDs，以空格分隔
     * @param contentTypes 知识类型，多个以空格分隔
     * @param sort 排序方式
     * @param bizIds 知识在原系统的ID，多个以空格分隔
     * @throws Exception
     */
    public void getRemoteKnowledge(Integer page,Integer pageSize,
                                   String key,String bizIds,
                                   String bizTypeIds,String contentTypes,
                                   String label1Ids,String label2Ids,
                                   Integer sort) throws Exception{
        if(page == null){
            page = 1;
        }
        if(pageSize == null){
            pageSize = DEFAULT_PAGE_SIZE;
        }
        String service = "http://vender.help.center.jd.local/?pagesize="+pageSize+"&page="+page+"&enc_url_gbk=yes";
        if(StringUtils.isNotEmpty(key)){
            key = URLEncoder.encode(key, "gb2312");
            service += "&key="+key;
        }

        service += joinFiltType(splitJointStr(bizIds),
                splitJointStr(bizTypeIds),splitJointStr(contentTypes),
                splitJointStr(label1Ids),splitJointStr(label2Ids));

        if(sort != null && KnowledgeSearchSortEnum.rightCode(sort)){
            String sortStr = KnowledgeSearchSortEnum.fromCode(sort).getDesc();
            service += "&sort_type="+sortStr;
        }
        try {
            String inputLine =  HttpClientUtil.sendGetRequest(service, "GBK");
            if(StringUtils.isNotEmpty(inputLine)){
                Map<String, Object> map = HttpClientUtil.json2Map(inputLine);
                if(map != null){
                    Map headMap = (Map)map.get("Head");
                    Map summaryMap = (Map)headMap.get("Summary");
                    String totalCount = (String)summaryMap.get("ResultCount");


                    Map objCollectionMap = (Map)map.get("ObjCollection");
                    List<Map<String, Object>> contentTypeList = (List<Map<String, Object>>)objCollectionMap.get("content_type");
                    List<Map<String, Object>> label1IdsList = (List<Map<String, Object>>)objCollectionMap.get("label1_ids");
                    List<Map<String, Object>> label2IdsList = (List<Map<String, Object>>)objCollectionMap.get("label2_ids");

                    List<Map<String, Object>> knowledgeBeanMapList = (List<Map<String, Object>>) map.get("Paragraph");
                    List<KnowledgeContentTypeBean> contentTypeBeanList = getContentTypeBean(contentTypeList);
                    List<KnowledgeLabelBean> label1Beans = getLabelBean(label1IdsList);
                    List<KnowledgeLabelBean> label2Beans = getLabelBean(label2IdsList);
                    List<KnowledgeBean> knowledgeBeanList = getKnowledgeBean(knowledgeBeanMapList);
                    System.out.println(totalCount);
                    System.out.println(contentTypeBeanList);
                    System.out.println(label1Beans);
                    System.out.println(label2Beans);
                    System.out.println(knowledgeBeanList);
                }
            }

        } catch (Exception e) {
            logger.error("getRemoteKnowledge error",e);
        }
    }

    private List<KnowledgeContentTypeBean> getContentTypeBean(List<Map<String, Object>> contentTypeList){
        List<KnowledgeContentTypeBean> resultList = new ArrayList<KnowledgeContentTypeBean>();
        if(CollectionUtils.isEmpty(contentTypeList)){
            return null;
        }
        for(Map<String, Object> map : contentTypeList){
            String idString = (String)map.get("Classification");
            String countStirng = (String)map.get("Count");
            KnowledgeContentTypeBean bean = new KnowledgeContentTypeBean();
            if(StringUtils.isNotEmpty(idString)){
                int id = Integer.parseInt(idString);
                if(KnowledgeContentTypesEnum.rightCode(id)){
                    bean.setId(id);
                    bean.setName(KnowledgeContentTypesEnum.fromCode(id).getDesc());
                    bean.setCount(StringUtils.isBlank(countStirng) ? 0 : Integer.parseInt(countStirng));
                    resultList.add(bean);
                }

            }

        }
        return resultList;
    }

    private List<KnowledgeLabelBean> getLabelBean(List<Map<String, Object>> labelIdsList){
        List<KnowledgeLabelBean> resultList = new ArrayList<KnowledgeLabelBean>();
        if(CollectionUtils.isEmpty(labelIdsList)){
            return null;
        }
        for(Map<String, Object> map : labelIdsList){
            String idString = (String)map.get("Classification");
            String countStirng = (String)map.get("Count");
            String name = (String)map.get("Name");
            String fidString = (String)map.get("FClassification");
            KnowledgeLabelBean bean = new KnowledgeLabelBean();
            bean.setId(StringUtils.isBlank(idString) ? null : Integer.parseInt(idString));
            bean.setFid(StringUtils.isBlank(fidString) ? null : Integer.parseInt(fidString));
            if(bean.getFid() == null || bean.getFid() == 0){
                bean.setLevel(0);//fid=0代表是一级标签
            }else{
                bean.setLevel(1);//fid != 0代表是二级标签
            }
            bean.setName(decodeStr(name));
            bean.setCount(StringUtils.isBlank(countStirng) ? 0 : Integer.parseInt(countStirng));
            resultList.add(bean);

        }
        return resultList;
    }

    private List<KnowledgeBean> getKnowledgeBean(List<Map<String, Object>> knowledgeBeanList){
        List<KnowledgeBean> resultList = new ArrayList<KnowledgeBean>();
        if(CollectionUtils.isEmpty(knowledgeBeanList)){
            return null;
        }
        for(Map<String, Object> map : knowledgeBeanList){
            KnowledgeBean bean = new KnowledgeBean();
            String bizIdStr = (String)map.get("biz_id");
            String bizTypeIdStr = (String)map.get("biz_type_id");
            String contentTypeStr = (String)map.get("content_type");
            String createTimeStr = (String)map.get("create_time");
            String expTimeStr = (String)map.get("exp_time");
            String idStr = (String)map.get("id");
            String modifyTimeStr = (String)map.get("modify_time");
            String pvStr = (String)map.get("pv");
            String solveCountStr = (String)map.get("solve_count");
            String unSolveCountStr = (String)map.get("unsolve_count");
            String uvStr = (String)map.get("uv");
            String statusStr = (String)map.get("status");
            String createdStr = (String)map.get("created");
            String modifiedStr = (String)map.get("modified");
            bean.setBizId(StringUtils.isBlank(bizIdStr) ? null : Long.parseLong(bizIdStr));
            bean.setBizTypeId(StringUtils.isBlank(bizTypeIdStr) ? null : Integer.parseInt(bizTypeIdStr));
            bean.setContentType(StringUtils.isBlank(contentTypeStr) ? null : Integer.parseInt(contentTypeStr));
            bean.setCreateTime(StringUtils.isBlank(createTimeStr) ? null : new Date(Long.parseLong(createTimeStr) * 1000));
            bean.setExpTime(StringUtils.isBlank(expTimeStr) ? null : new Date(Long.parseLong(expTimeStr) * 1000));
            bean.setModifyTime(StringUtils.isBlank(modifyTimeStr) ? null : new Date(Long.parseLong(modifyTimeStr) * 1000));
            bean.setCreated(StringUtils.isBlank(createdStr) ? null : new Date(Long.parseLong(createdStr) * 1000));
            bean.setModified(StringUtils.isBlank(modifiedStr) ? null : new Date(Long.parseLong(modifiedStr) * 1000));
            bean.setId(StringUtils.isBlank(idStr) ? null : Long.parseLong(idStr));
            bean.setPv(StringUtils.isBlank(pvStr) ? null : Integer.parseInt(pvStr));
            bean.setUv(StringUtils.isBlank(uvStr) ? null : Integer.parseInt(uvStr));
            bean.setSolveCount(StringUtils.isBlank(solveCountStr) ? null : Integer.parseInt(solveCountStr));
            bean.setUnsolveCount(StringUtils.isBlank(unSolveCountStr) ? null : Integer.parseInt(unSolveCountStr));
            bean.setStatus(StringUtils.isBlank(statusStr) ? null : Integer.parseInt(statusStr));
            Map contentMap = (Map)map.get("Content");
            if(contentMap != null){
                String keyword = (String)contentMap.get("keyword");
                String label1Ids = (String)contentMap.get("label1_ids");
                String label1Names = (String)contentMap.get("label1_names");
                String label2Ids = (String)contentMap.get("label2_ids");
                String label2Names = (String)contentMap.get("label2_names");
                String name = (String)contentMap.get("name");
                String summary = (String)contentMap.get("summary");
                String url = (String)contentMap.get("url");
                String picUrl = (String)contentMap.get("pic_url");
                String bizTypeName = (String)contentMap.get("biz_type_name");
                String content = (String)contentMap.get("content");
                String validTimeStr = (String)map.get("valid_time");
                bean.setValidTime(StringUtils.isBlank(validTimeStr) ? null : new Date(Long.parseLong(validTimeStr) * 1000));
                bean.setLabel1Ids(decodeStr(label1Ids));
                bean.setLabel1Names(decodeStr(label1Names));
                bean.setLabel2Ids(decodeStr(label2Ids));
                bean.setLabel2Names(decodeStr(label2Names));
                bean.setName(decodeStr(name));
                bean.setSummary(decodeStr(summary));
                bean.setUrl(decodeStr(url));
                bean.setPicUrl(decodeStr(picUrl));
                bean.setBizTypeName(decodeStr(bizTypeName));
                bean.setContent(decodeStr(content));
                bean.setKeyword(decodeStr(keyword));
            }
            resultList.add(bean);
        }
        return resultList;
    }

    /**
     * 将以空格分隔的字符串，拼接成LM类型的字符串
     * 例如，参数：1 2 3
     * 返回值：L1M1||L2M2||L3M3
     * @param str
     * @return
     */
    private String splitJointStr(String str){
        if(StringUtils.isBlank(str)){
            return "";
        }
        String[] strArr = str.split(" ");
        String resultStr = "";
        for(int i = 0 ;i < strArr.length; i++){
            resultStr += "L"+strArr[i]+"M"+strArr[i];
            if(i < strArr.length - 1){
                //"||"编码之后的结果，因为http请求不认这个符号
                resultStr += "%7c%7c";
            }
        }
        return resultStr;
    }

    /**
     * 拼装过滤字段
     * @param bizIds
     * @param bizTypeIds
     * @param contentTypes
     * @param label1Ids
     * @param label2Ids
     * @return
     */
    private String joinFiltType(String bizIds,
                                String bizTypeIds,String contentTypes,
                                String label1Ids,String label2Ids){
        String resultStr = "";
        if(StringUtils.isNotEmpty(bizIds) || StringUtils.isNotEmpty(bizTypeIds) ||
                StringUtils.isNotEmpty(contentTypes) || StringUtils.isNotEmpty(label1Ids) || StringUtils.isNotEmpty(label2Ids)){
            resultStr = "&filt_type=";
        }
        List<String> resultList = new ArrayList<String>();
        if(StringUtils.isNotEmpty(bizIds)){
            bizIds = "biz_id,"+bizIds;
            resultList.add(bizIds);
        }
        if(StringUtils.isNotEmpty(bizTypeIds)){
            bizTypeIds = "biz_type_id,"+bizTypeIds;
            resultList.add(bizTypeIds);
        }
        if(StringUtils.isNotEmpty(contentTypes)){
            contentTypes = "content_type,"+contentTypes;
            resultList.add(contentTypes);
        }
        if(StringUtils.isNotEmpty(label1Ids)){
            label1Ids = "label1_ids,"+label1Ids;
            resultList.add(label1Ids);
        }
        if(StringUtils.isNotEmpty(label2Ids)){
            label2Ids = "label2_ids,"+label2Ids;
            resultList.add(label2Ids);
        }
        for(int i=0 ; i < resultList.size(); i++){
            resultStr += resultList.get(i);
            if(i < resultList.size() - 1){
                resultStr += ";";
            }
        }
        return resultStr;
    }

    private String decodeStr(String str){
        if(StringUtils.isBlank(str)){
            return str;
        }
        try {
            str = URLDecoder.decode(str, "gbk");
        } catch (UnsupportedEncodingException e) {
            logger.error("decodeStr error,str="+str,e);
        }
        return str;
    }
}
