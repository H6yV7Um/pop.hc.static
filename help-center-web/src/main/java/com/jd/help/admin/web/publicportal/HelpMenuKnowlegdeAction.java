package com.jd.help.admin.web.publicportal;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.jd.common.util.StringUtils;
import com.jd.common.web.result.Result;
import com.alibaba.fastjson.JSONObject;
import com.jd.help.HelpBaseAction;
import com.jd.help.configCenter.ConfigCenterUtils;
import com.jd.help.configCenter.ConfigConstants;
import com.jd.help.dao.publicportal.HelpMenuKnowledgeMapper;
import com.jd.help.dao.publicportal.HelpMenuLabelMapper;
import com.jd.help.domain.knowledge.KnowledgeEsBean;
import com.jd.help.domain.publicportal.HelpMenuKnowledge;
import com.jd.help.domain.publicportal.HelpMenuLabel;
import com.jd.help.enums.KnowledgeContentTypesEnum;
import com.jd.help.jimdb.JimdbConstants;
import com.jd.help.service.PublicPortalService;
import com.jd.jim.cli.Cluster;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaojianhong on 2018/4/17.
 */
public class HelpMenuKnowlegdeAction extends HelpBaseAction {
    private final Logger logger = LoggerFactory.getLogger(HelpMenuKnowlegdeAction.class);

    private Map<String, Object> jsonData = new HashMap<String, Object>();

    private HelpMenuKnowledge helpMenuKnowledge;

    private HelpMenuLabel helpMenuLabel;

    private KnowledgeEsBean knowledgeEsBean;

    @Resource
    private HelpMenuKnowledgeMapper helpMenuKnowledgeMapper;

    @Resource
    private HelpMenuLabelMapper helpMenuLabelMapper;

    @Resource
    private PublicPortalService publicPortalService;

    @Resource(name = "jimClient")
    private Cluster jimClient;

    @Resource
    private ConfigCenterUtils configCenterUtils;

    private final LoadingCache<String, List<KnowledgeEsBean>> guavaCache = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .softValues()
            .concurrencyLevel(10)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build(new CacheLoader<String, List<KnowledgeEsBean>>() {
                @Override
                public List<KnowledgeEsBean> load(String key) throws Exception {
                    return publicPortalService.getReturnSource(key);
                }
            });

    public String insert() {

        try {
            jsonData.put("token", "faild");
            helpMenuKnowledge.setMenuId(666);
            helpMenuKnowledgeMapper.insert(helpMenuKnowledge);
        } catch (Exception e) {
            logger.info("HelpMenuKnowlegdeAction . insert has error ", e);
        } finally {
        }
        jsonData.put("token", "success");
        return "json";
    }

    public String update() {
        try {
            jsonData.put("token", "faild");
            HelpMenuKnowledge validBean = helpMenuKnowledgeMapper.selectByMenuId(helpMenuKnowledge);
            if (validBean == null) {
                helpMenuKnowledgeMapper.insert(helpMenuKnowledge);
            } else {
                String[] arrNew = validBean.getNewKnowledgeId() == null ? new String[0] : validBean.getNewKnowledgeId().trim().split(" ");
                StringBuffer stbNew = new StringBuffer();
                String nid = helpMenuKnowledge.getNewKnowledgeId() == null ? "" : helpMenuKnowledge.getNewKnowledgeId();
                stbNew.append(nid);
                String cid = helpMenuKnowledge.getChangeKnowledgeId() == null ? "" : helpMenuKnowledge.getChangeKnowledgeId();
                for (String str : arrNew) {
                    if (!cid.equals(str) && StringUtils.isNotEmpty(str)) {
                        stbNew.append(" " + str);
                    }
                }
                helpMenuKnowledge.setNewKnowledgeId(stbNew.toString());
                String[] arChange = validBean.getChangeKnowledgeId() == null ? new String[0] : validBean.getChangeKnowledgeId().trim().split(" ");
                StringBuffer stbChange = new StringBuffer();
                stbChange.append(cid);
                for (String str : arChange) {
                    if (!nid.equals(str) && StringUtils.isNotEmpty(str)) {
                        stbChange.append(" " + str);
                    }
                }
                helpMenuKnowledge.setChangeKnowledgeId(stbChange.toString());
                helpMenuKnowledgeMapper.updateByMenuId(helpMenuKnowledge);
            }
            jimClient.set(JimdbConstants.KEY_PREFIX_ONE + helpMenuKnowledge.getMenuId(), JSONObject.toJSONString(helpMenuKnowledge));
        } catch (Exception e) {
            logger.info("HelpMenuKnowlegdeAction . update has error ", e);
        } finally {
        }
        jsonData.put("token", "success");
        return "json";
    }

    public String delete() {
        return SUCCESS;
    }

    /**
     * 1.根据menuId和menuDesc查询确认是否是正确的menuId
     * 2.根据menuId查询标签
     * 3.根据标签查询知识
     *
     * @return
     */
    public String select() {
        try {
            Result result = new Result(true);
            jsonData.put("token", "faild");
            List<HelpMenuLabel> list = helpMenuLabelMapper.validCheck(helpMenuLabel);
            if (list.size() > 0) {
                HelpMenuLabel helpMenuLabel1 = new HelpMenuLabel();
                helpMenuLabel1 = list.get(0);
                result.addDefaultModel("helpMenuLabel1", helpMenuLabel1);
                List<KnowledgeEsBean> listResult = new ArrayList<KnowledgeEsBean>();
                if (helpMenuLabel != null && (StringUtils.isNotBlank(helpMenuLabel.getMenuDesc()) || helpMenuLabel.getMenuId() != null)) {
                    listResult = guavaCache.get(JimdbConstants.KEY_PREFIX_FIVE + helpMenuLabel1.getMenuId());
                }
                result.addDefaultModel("list", listResult);
                toVm(result);
            } else {
                return SUCCESS;
            }
        } catch (Exception e) {
            logger.info("HelpMenuKnowlegdeAction . select has error ", e);
        } finally {
        }
        return SUCCESS;
    }

    public String getNewKnowledgeList() {
        try {
            jsonData.put("token", "faild");
            List<KnowledgeEsBean> list = publicPortalService.getNewKnowledgeList(helpMenuKnowledge, knowledgeEsBean);
            HelpMenuKnowledge helpMenuKnowledge1 = helpMenuKnowledgeMapper.selectByMenuId(helpMenuKnowledge);
            Set<Long> set = new HashSet<Long>();
            if (helpMenuKnowledge1 != null) {
                String[] arChange = helpMenuKnowledge1.getChangeKnowledgeId().split(" ");
                for (String str : arChange) {
                    set.add(Long.valueOf(str));
                }
            }
            if (set.size() > 0) {
                Iterator<KnowledgeEsBean> it = list.iterator();
                while (it.hasNext()) {
                    if (set.contains(it.next().getKnowledgeId())) {
                        it.remove();
                    }
                }
            }
            if (list.size() > 10) {
                list = list.subList(0, 10);
            }
            for (KnowledgeEsBean bean : list) {
                String[] arr1 = bean.getLabel1Names() == null ? new String[0] : bean.getLabel1Names().split(" ");
                String[] arr2 = bean.getLabel2Names() == null ? new String[0] : bean.getLabel2Names().split(" ");
                String name = "";
                if (arr1.length == arr2.length) {
                    for (int n = 0; n < arr1.length; n++) {
                        name = name + arr1[n] + "->" + arr2[n] + " ";
                    }
                } else {
                    logger.info("该条知识存在异常,knowledgeId = {}", bean.getKnowledgeId());
                }
                bean.setLabel2Names(name);
                bean.setContent(KnowledgeContentTypesEnum.fromCode(bean.getContentType()) == null ? "" : KnowledgeContentTypesEnum.fromCode(bean.getContentType()).getDesc());
                bean.setSummary(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(bean.getCreateTime())));
            }
            jsonData.put("data", list);
        } catch (Exception e) {
            logger.info("HelpMenuKnowlegdeAction . getNewKnowledgeList has error ", e);
        } finally {
        }
        jsonData.put("token", "success");
        return "json";
    }

    public String getKnowledgeType() {
        try {
            jsonData.put("token", "faild");
            List<String> list = new ArrayList<String>();
            for (KnowledgeContentTypesEnum e : KnowledgeContentTypesEnum.values()) {
                list.add(e.getDesc());
            }
            jsonData.put("data", list);
        } catch (Exception e) {
            logger.info("HelpMenuKnowlegdeAction . getKnowledgeType has error ", e);
        } finally {
        }
        jsonData.put("token", "success");
        return "json";
    }

    public String getconfig() {
        try {
            jsonData.put("token", "faild");
            logger.info("before");
            Boolean b = configCenterUtils.isOpenSwitch(ConfigConstants.PUBLIC_PORTAL_SHOP);
            logger.info("after");
            jsonData.put("data", b);
        } catch (Exception e) {
            logger.info("HelpMenuKnowlegdeAction . getconfig has error ", e);
        } finally {
        }
        jsonData.put("token", "success");
        return "json";
    }

    /**
     * 商家后台知识推荐接口
     *
     * @return
     */
    public String getData() {
        Long startTime = System.currentTimeMillis();
        CallerInfo info = Profiler.registerInfo("vender_help_center.HelpMenuKnowlegdeAction.getData","vender_help_center", false, true);
        jsonData.put("token", "0");
        List<KnowledgeEsBean> listResult = new ArrayList<KnowledgeEsBean>();
        try {
            if (configCenterUtils.isOpenSwitch(ConfigConstants.PUBLIC_PORTAL_SHOP)) {
                logger.info("HelpMenuKnowlegdeAction.getData 入参 = {}", helpMenuLabel.getMenuId());
                listResult = guavaCache.get(JimdbConstants.KEY_PREFIX_FIVE + helpMenuLabel.getMenuId());
                jsonData.put("data", listResult);
                jsonData.put("token", "1");
            }
        } catch (Exception e) {
            logger.info("vender_help_center.HelpMenuKnowlegdeAction.getData has error", e);
            Profiler.functionError(info);
        } finally {
            Profiler.registerInfoEnd(info);
            logger.info("HelpMenuKnowlegdeAction.getData 出参 = {}", JSONObject.toJSONString(listResult));
        }
        Long endTime = System.currentTimeMillis();
        logger.info("HelpMenuKnowlegdeAction.getData 耗时 = {}", endTime - startTime);
        return "json";
    }

    public String testVm() {
        return "form";
    }

    public Map<String, Object> getJsonData() {
        return jsonData;
    }

    public void setJsonData(Map<String, Object> jsonData) {
        this.jsonData = jsonData;
    }

    public HelpMenuKnowledge getHelpMenuKnowledge() {
        return helpMenuKnowledge;
    }

    public void setHelpMenuKnowledge(HelpMenuKnowledge helpMenuKnowledge) {
        this.helpMenuKnowledge = helpMenuKnowledge;
    }

    public HelpMenuLabel getHelpMenuLabel() {
        return helpMenuLabel;
    }

    public void setHelpMenuLabel(HelpMenuLabel helpMenuLabel) {
        this.helpMenuLabel = helpMenuLabel;
    }

    public KnowledgeEsBean getKnowledgeEsBean() {
        return knowledgeEsBean;
    }

    public void setKnowledgeEsBean(KnowledgeEsBean knowledgeEsBean) {
        this.knowledgeEsBean = knowledgeEsBean;
    }
}
