package com.jd.help.service.impl;

import com.jd.common.web.result.Result;
import com.jd.help.dao.SiteDao;
import com.jd.help.domain.Site;
import com.jd.help.service.SiteService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Site service ʵ��
 * generated by bud
 *
 * @author @laichendong
 */
@Service("siteService")
public class SiteServiceImpl implements SiteService {
    private static final Log log = LogFactory.getLog(SiteServiceImpl.class);

    @Resource
    private SiteDao siteDao;

    public Result list(Site site, int page, int pageSize) {
        Result result = new Result();
        if (site == null) {
            return result;
        }
//        PaginatedList<Site> siteList = siteDao.queryForList(site, page, pageSize);
//        result.setSuccess(true);
//        result.addDefaultModel("siteList", siteList);
        return result;
    }

    public Result insert(Site site) {
        Result result = new Result();
        if (site == null) {
            result.setSuccess(false);
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
        Long id = siteDao.insert(site);
        if (id <= 0) {
            result.setSuccess(false);
            result.setResultCode("system.insert.error");
        } else {
            result.setSuccess(true);
            result.setResultCode("system.insert.success");
        }
        return result;
    }

    public Result update(Site site) {
        Result result = new Result();
        try {
            if (site == null || site.getId() == null || site.getId() <= 0) {
                result.setSuccess(false);
                result.setResultCode("system.no.entity.to.operate");
                return result;
            }



            // �ĳ�Ĭ��վ�� ���жϣ�ֻ����һ��Ĭ��վ�� �ұ��뿪��
            if (site.getDefaultSite() == 1) {

                // Ĭ��վ�����Ϊ����״̬
                if (site.getStatus() == 0) {
                    result.setSuccess(false);
                    result.setResultCode("Ĭ��վ�����Ϊ����״̬");
                    return result;
                }

                Result r = findDefaultSite();
                if (r.isSuccess()) {
                    Site defaultSite = (Site) r.get("site");
                    if (defaultSite != null && !defaultSite.getId().equals(site.getId())) {
                        result.setSuccess(false);
                        result.setResultCode("ϵͳ��ֻ����һ��Ĭ��վ��");
                        return result;
                    }
                }
            }

            int i = siteDao.update(site);
            result.setSuccess(true);
            if (i <= 0) {
                result.setSuccess(false);
                result.setResultCode("info.site.load.error");
            }
        } catch (Exception e) {
            log.error("Method:update----------->" + e.getMessage());
            result.setResultCode("info.site.load.error");
            result.setSuccess(false);
        }
        return result;
    }

    public Result delete(Site site) {
        Result result = new Result();
        try {
            if (site == null || site.getId() == null || site.getId() <= 0) {
                result.setSuccess(false);
                result.setResultCode("system.no.entity.to.operate");
                return result;
            }

            int i = siteDao.delete(site);
            result.setSuccess(true);
            if (i <= 0) {
                result.setSuccess(false);
                result.setResultCode("info.site.delete.error");
            }

        } catch (Exception e) {
            log.error("Method:delete----------->" + e.getMessage());
            result.setResultCode("info.site.delete .error");
            result.setSuccess(false);
        }
        return result;
    }

    public Result listAll() {
        Result result = new Result();

        try {
            List<Site> list = siteDao.queryAllForList();
            result.setSuccess(true);
            if (list.size() > 0) {
                result.addDefaultModel("sites", list);
            } else {
                result.setResultCode("info.sys.load.error");
            }
        } catch (Exception e) {
            log.error("Method:listAll----------->" + e.getMessage());
            result.setResultCode("info.sites.load.error");
            result.setSuccess(false);
        }
        return result;
    }

    public Result findOne(Site site) {
        Result result = new Result();

        try {
            Site qSite = siteDao.findOne(site);
            if (qSite != null) {
                result.setSuccess(true);
                result.addDefaultModel("site", qSite);
            } else {
                result.setSuccess(false);
                result.setResultCode("info.site.load.error");
            }

        } catch (Exception e) {
            log.error("Method:findOne----------->" + e.getMessage());
            result.setResultCode("info.site.load.error");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result findDefaultSite() {
        Result result = new Result();

        try {
            Site site = siteDao.findDefaultSite();
            if (site != null) {
                result.setSuccess(true);
                result.addDefaultModel("site", site);
            } else {
                result.setSuccess(false);
                result.setResultCode("info.site.load.error");
            }

        } catch (Exception e) {
            log.error("Method:findOne----------->" + e.getMessage());
            result.setResultCode("info.site.load.error");
            result.setSuccess(false);
        }
        return result;

    }
}