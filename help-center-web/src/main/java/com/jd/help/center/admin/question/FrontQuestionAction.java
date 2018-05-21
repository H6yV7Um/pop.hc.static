package com.jd.help.center.admin.question;

import com.jd.common.util.StringUtils;
import com.jd.common.web.result.Result;
import com.jd.fce.promise.pfp.service.helppage.PromiseIconService;
import com.jd.help.center.domain.area.AreaListBeanVO;
import com.jd.help.center.domain.border.BorderInfo;
import com.jd.help.center.domain.question.HelpQuestion;
import com.jd.mongodbclient2.MongoDBClient;
import com.mongodb.QueryBuilder;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.Cookie;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-12
 * Time: 11:07:26
 * To change this template use File | Settings | File Templates.
 */
public class FrontQuestionAction extends FrontBaseAction {
    private static final Log log = LogFactory.getLog(FrontQuestionAction.class);


    private int questionId;

    private String cjggTag = "false";

    /**
     * ��ַ����
     */
    private Map<Integer, List<AreaListBeanVO>> areaMap;

    /**
     * �����ͷ���
     */
    private Map<Integer, Set<Integer>> districts;

    /**
     * ��������
     */
    private Map<Integer, String> serviceDescriptionMap;

    /**
     * ʡID
     */
    private Integer provinceId;

    /**
     * ��ID
     */
    private Integer cityId;

    /**
     * ��ID
     */
    private Integer areaId;

    /**
     * ���ļ���ַID
     */
    private Integer fouthId;

    /**
     * ���ͷ����ѯҳ���Ƿ���ʾ�˵�����ǰ̨��Ʒҳ��ʹ�ò�����ʾ���˵���
     */
    private String showType;

    /**
     * promise�ṩ��saf�ӿ�
     */
    private PromiseIconService iconService;


    private MongoDBClient helpBorderMgDao;

    //����ϵͳ��ҳkey
    private String systemIndex;


    private String defaultAddressCookie = "1-72-2799";


    //    ��ȡ��̬����ҳ��
    public String getStaticZiti() throws Exception {
        ValueStack context = ActionContext.getContext().getValueStack();
        Result result = leftNavigateService.getLeftNavigateResult(sysName);
        result.addDefaultModel("topicId", topicId);
        if (result.isSuccess()) {
            //��ȡ�ļ���ַcookie For GIS
            Cookie addressCookie = getAddressCookie();
            if (addressCookie == null) {
                //Ĭ���ļ���ַ������������������
                String defaultAddressCookie = "1-72-2799";
                GISUrl = GISUrl + "?pid=1&cityid=72&cid=2799&sid=&code=";
                writeAddressCookie(defaultAddressCookie);
            } else {
                String addressCookieArray[] = addressCookie.getValue().split("-");
                switch (addressCookieArray.length) {
                    case 1: {
                        GISUrl = GISUrl + "?pid=" + addressCookieArray[0] + "&cityid=&cid=&sid=&code=";
                        break;
                    }
                    case 2: {
                        GISUrl = GISUrl + "?pid=" + addressCookieArray[0] + "&cityid=" + addressCookieArray[1] + "&cid=&sid=&code=";
                        break;
                    }
                    case 3: {
                        GISUrl = GISUrl + "?pid=" + addressCookieArray[0] + "&cityid=" + addressCookieArray[1] + "&cid=" + addressCookieArray[2] + "&sid=&code=";
                        break;
                    }
                    case 4: {
                        GISUrl = GISUrl + "?pid=" + addressCookieArray[0] + "&cityid=" + addressCookieArray[1] + "&cid=" + addressCookieArray[2] + "&sid=" + addressCookieArray[3] + "&code=";
                        break;
                    }
                }
            }
            context.set("isStatic", "1");
            context.set("GISUrl", GISUrl);

            Set<String> set = result.keySet();
            for (String key : set) {
                context.set(key, result.get(key));
            }
            return doExecute();
        } else {
            toVm(result);
            return ERROR;
        }
    }

    public String execute() throws Exception {
        ValueStack context = ActionContext.getContext().getValueStack();
        Result result = leftNavigateService.getLeftNavigateResult(sysName);
        result.addDefaultModel("topicId", topicId);
        log.error("-------------result.isSuccess:"+result.isSuccess());
        if (result.isSuccess()) {
//            �ж��Ƿ�Ϊ����ҳ����
        log.error("-------------topicId:"+topicId);
            if (topicId == 64) {
                String provinceId = request.getParameter("provinceId");
                log.error("-------------provinceId:"+provinceId);
                if (StringUtils.isNotBlank(provinceId)) {
                    GISUrl = getGISUrlFromUrl();
                } else {
                    GISUrl = getGISUrlFromCookie();
                }
                log.error("-------------topicId:"+GISUrl);
                context.set("GISUrl", GISUrl);
                }
            Set<String> set = result.keySet();
            for (String key : set) {
                context.set(key, result.get(key));
            }
            return doExecute();
        } else {
            toVm(result);
            return ERROR;
        }
    }

    //��cookie�л�ȡgisurl
    private String getGISUrlFromCookie() {
        String defaultGISUrl = GISUrl + "?pid=1&cityid=72&cid=2799&sid=&code=";
        //��ȡ�ļ���ַcookie For GIS
        Cookie addressCookie = getAddressCookie();

        if (addressCookie == null) {
            //Ĭ���ļ���ַ������������������
            writeAddressCookie(defaultAddressCookie);
            return defaultGISUrl;
        } else {
            String addressCookieArray[] = addressCookie.getValue().split("-");
            switch (addressCookieArray.length) {
                case 1: {
                    GISUrl = GISUrl + "?pid=" + addressCookieArray[0] + "&cityid=&cid=&sid=&code=";
                    break;
                }
                case 2: {
                    GISUrl = GISUrl + "?pid=" + addressCookieArray[0] + "&cityid=" + addressCookieArray[1] + "&cid=&sid=&code=";
                    break;
                }
                case 3: {
                    GISUrl = GISUrl + "?pid=" + addressCookieArray[0] + "&cityid=" + addressCookieArray[1] + "&cid=" + addressCookieArray[2] + "&sid=&code=";
                    break;
                }
                case 4: {
                    GISUrl = GISUrl + "?pid=" + addressCookieArray[0] + "&cityid=" + addressCookieArray[1] + "&cid=" + addressCookieArray[2] + "&sid=" + addressCookieArray[3] + "&code=";
                    break;
                }
            }
            return GISUrl;
        }
    }

    //��url�����л�ȡgisurl
    private String getGISUrlFromUrl() {
        String defaultGISUrl = GISUrl + "?pid=1&cityid=72&cid=2799&sid=&code=";

        Integer provinceId = Integer.valueOf(request.getParameter("provinceId"));
        Integer cityId = Integer.valueOf(request.getParameter("cityId"));
        Integer townId = Integer.valueOf(request.getParameter("townId"));
        Integer forthId = Integer.valueOf(request.getParameter("forthId"));
        Integer code = Integer.valueOf(request.getParameter("code"));

//        String urlAddress = (StringUtils.isEmpty(provinceId)?0:provinceId) + "-" + (StringUtils.isEmpty(cityId)?0:cityId) + "-" + (StringUtils.isEmpty(townId)?0:townId) + "-" + (StringUtils.isEmpty(forthId)?0:forthId);
//        ͨ�������ID��ȡ�����
        if (code != 0) {
            return GISUrl + "?pid=&cityid=&cid=&sid=&code=" + code;
        } else {
//            writeAddressCookie(urlAddress);
            return GISUrl + "?pid=" + provinceId + "&cityid=" + cityId + "&cid=" + townId + "&sid=" + forthId + "&code=";
        }
    }

    //    У���ļ���ַ�Ƿ�Ϸ�
    private boolean validateAddress(Integer provinceId, Integer cityId, Integer townId, Integer forthId) {
        boolean isValidate = false;
        if (provinceId == 0) {
            return isValidate;
        }
//        String addressCookieArray[] = address.split("-");
        // ʡ�б�
        List<AreaListBeanVO> provinceList = areaMap.get(0);
        String provinceName = "";
        String cityName = "";
        String townName = "";
        String forthName = "";
        provinceName = getAddressNameById(provinceId, provinceList);
        if (StringUtils.isNotBlank(provinceName) && cityId == 0) {
            isValidate = true;
        } else {
            cityName = getAddressNameById(cityId, areaMap.get(provinceId));
            if (StringUtils.isNotBlank(cityName) && townId == 0) {
                isValidate = true;
            } else {
                townName = getAddressNameById(townId, areaMap.get(cityId));
                if (StringUtils.isNotBlank(townName) && forthId == 0) {
                    isValidate = true;
                } else {
                    forthName = getAddressNameById(forthId, areaMap.get(townId));
                    if (StringUtils.isNotBlank(forthName)) {
                        isValidate = true;
                    }
                }
            }
        }
        return isValidate;
    }

    @Override
    public String doExecute() throws Exception {
        Result result = new Result();
        result.setSuccess(true);
        if (topicId > 0) {
            result = frontQuestionService.getQuestion(sysName, topicId);
        }
        toVm(result);
        if (result.getSuccess())
            return SUCCESS;
        return ERROR;
    }

    /**
     * ��ȡҳ��cookie
     */
    public Cookie getAddressCookie() {
        Cookie cookies[] = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            Cookie addressCookie = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("address")) {
                    addressCookie = cookie;
                    break;
                }
            }
            return addressCookie;
        }
        return null;
    }

    /**
     * �����ļ���ַcookie
     */
    public void writeAddressCookie(String value) {
        if (StringUtils.isNotEmpty(value)) {
            Cookie cookie = new Cookie("address", value);
            cookie.setPath("/");
            cookie.setDomain(".jd.com");
            cookie.setMaxAge(60 * 60 * 24 * 7);//����cookieĬ���������ڣ����죩
            response.addCookie(cookie);
        }
    }

    /**
     * ����ĳһ�㼶��ַ��id��ȡ��Ӧ������
     */
    private String getAddressNameById(Integer id, List<AreaListBeanVO> areaList) {
        if (null != id && CollectionUtils.isNotEmpty(areaList)) {
            for (AreaListBeanVO areaListBeanVO : areaList) {
                if (areaListBeanVO.getId() == id) {
                    return areaListBeanVO.getName();
                }
            }
        }
        return "";
    }

    public String previewQuestion() {
        this.setResult(frontQuestionService.previewQuestion(topicId));
        result.addDefaultModel("questionId", questionId);
        this.toVm(result);
        return SUCCESS;
    }

    public String viewQuestion() {
        Result result = leftNavigateService.getLeftNavigateResult(sysName);
        Result resultQuestion = frontQuestionService.previewQuestion(topicId);
        result.addDefaultModel("helpTopic", resultQuestion.get("helpTopic"));
        List<HelpQuestion> list = (List<HelpQuestion>) resultQuestion.get("questions");
        if (list == null || list.size() < 1 || questionId < 1)
            return ERROR;
        HelpQuestion question = null;
        for (HelpQuestion q : list) {
            if (questionId == q.getQuestionId()) {
                question = q;
                break;
            }
        }
        if (question == null)
            return ERROR;
        result.addDefaultModel("viewQuestion", question);
        toVm(result);
        return SUCCESS;
    }


    public void getIndexPage() {
        boolean flag = false;
        PrintWriter out = null;
        String indexString = "";
        if (StringUtils.isBlank(systemIndex)) {
            systemIndex = "index";
        }
        try {
            BorderInfo borderInfo = (BorderInfo) helpBorderMgDao.selectOne(QueryBuilder.start(BorderInfo.HELP_BORDER_KEY).is(systemIndex + "Page"), BorderInfo.class);

            if (borderInfo == null || StringUtils.isBlank(borderInfo.getContent())) {
                throw new RuntimeException("������ҳ��Ϣ����systemIndex=" + systemIndex);
            }
            indexString = borderInfo.getContent();
            flag = true;
        } catch (Exception e) {
            log.error("Method: get index page error ,", e);
        }
        try {
            response.setContentType("text/html;charset=utf-8");
            out = response.getWriter();
            out.write(indexString.replaceAll("http://","//"));
            out.flush();
        } catch (Exception e) {
            log.error("������ҳ��Ϣ����systemIndex=" + systemIndex, e);
            throw new RuntimeException("������ҳ��Ϣ����systemIndex=" + systemIndex);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setHelpBorderMgDao(MongoDBClient helpBorderMgDao) {
        this.helpBorderMgDao = helpBorderMgDao;
    }

    public Map<Integer, List<AreaListBeanVO>> getAreaMap() {
        return areaMap;
    }

    public void setAreaMap(Map<Integer, List<AreaListBeanVO>> areaMap) {
        this.areaMap = areaMap;
    }

    public Map<Integer, Set<Integer>> getDistricts() {
        return districts;
    }

    public void setDistricts(Map<Integer, Set<Integer>> districts) {
        this.districts = districts;
    }


    public Map getServiceDescriptionMap() {
        return serviceDescriptionMap;
    }

    public void setServiceDescriptionMap(Map serviceDescriptionMap) {
        this.serviceDescriptionMap = serviceDescriptionMap;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getFouthId() {
        return fouthId;
    }

    public void setFouthId(Integer fouthId) {
        this.fouthId = fouthId;
    }

    public void setSystemIndex(String systemIndex) {
        this.systemIndex = systemIndex;
    }

    public String getShowType() {
        return showType;
    }


    public void setShowType(String showType) {
        this.showType = showType;
    }

    public PromiseIconService getIconService() {
        return iconService;
    }

    public void setIconService(PromiseIconService iconService) {
        this.iconService = iconService;
    }

    public String getCjggTag() {
        return cjggTag;
    }

    public void setCjggTag(String cjggTag) {
        this.cjggTag = cjggTag;
    }
}