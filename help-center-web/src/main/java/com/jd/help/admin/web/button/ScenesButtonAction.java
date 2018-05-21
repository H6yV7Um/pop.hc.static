package com.jd.help.admin.web.button;

import com.jd.common.web.result.Result;
import com.jd.help.HelpBaseAction;
import com.jd.help.admin.web.common.NoHrmPrivilegeException;
import com.jd.help.domain.*;
import com.jd.help.service.CatalogService;
import com.jd.help.service.ScenesButtonRelService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lining7 on 2017/10/10.
 */
@Controller
public class ScenesButtonAction extends HelpBaseAction {

    @Resource
    private ScenesButtonRelService scenesButtonRelService;

    @Resource
    private CatalogService catalogService;

    private ScensbuttonRelBO scensbuttonRelBO;

    //是否验证权限
    private final static boolean IS_VALIDATE = HrmPurviewConstants.IS_VALIDATE;

    private Integer cataId;

    private Integer cataPid;


    public String doInsert() throws NoHrmPrivilegeException {
        site = new Site();
        site.setId(scensbuttonRelBO.getSiteId());
        initSite(IS_VALIDATE);
        Result result = scenesButtonRelService.update(scensbuttonRelBO);
        Catalog catalog = catalogService.getCatalogById(scensbuttonRelBO.getCataId());
        result.addDefaultModel("cataId", null == catalog ? scensbuttonRelBO.getCataId() : catalog.getPid());
        this.toVm(result);
        return "updateResult";
    }

    public String set() throws NoHrmPrivilegeException {
        initSite(IS_VALIDATE);
        Result result = new Result();
        ScenesButtonRel condition = new ScenesButtonRel();
        condition.setRelId(cataId);
        condition.setType(1);
        List<ScenesButtonRel> list = scenesButtonRelService.queryForList(condition);
        ScensbuttonRelBO buttonRelBO = new ScensbuttonRelBO();
        buttonRelBO.setScenesButtonRelList(list);
        buttonRelBO.setSiteId(site.getId());
        result.addDefaultModel("scensbuttonRelBO", buttonRelBO);
        result.addDefaultModel("cataPid",cataPid);
        this.toVm(result);
        return SUCCESS;
    }

    public Integer getCataId() {
        return cataId;
    }

    public void setCataId(Integer cataId) {
        this.cataId = cataId;
    }

    public ScensbuttonRelBO getScensbuttonRelBO() {
        return scensbuttonRelBO;
    }

    public void setScensbuttonRelBO(ScensbuttonRelBO scensbuttonRelBO) {
        this.scensbuttonRelBO = scensbuttonRelBO;
    }

    public Integer getCataPid() {
        return cataPid;
    }

    public void setCataPid(Integer cataPid) {
        this.cataPid = cataPid;
    }
}
