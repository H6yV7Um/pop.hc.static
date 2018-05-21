package com.jd.help.service.impl;

import com.jd.common.web.result.Result;
import com.jd.help.dao.ScenesButtonRelDao;
import com.jd.help.domain.ScenesButtonRel;
import com.jd.help.domain.ScensbuttonRelBO;
import com.jd.help.service.ScenesButtonRelService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lining7 on 2017/10/10.
 */
@Service("scenesButtonRelService")
public class ScenesButtonRelServiceImpl implements ScenesButtonRelService {
    @Resource
    private ScenesButtonRelDao scenesButtonRelDao;
    @Override
    public Result insert(List<ScenesButtonRel> scenesButtonRelList) {
        Result result = new Result();
        if (CollectionUtils.isEmpty(scenesButtonRelList)) {
            result.setSuccess(false);
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
        scenesButtonRelDao.insert(scenesButtonRelList);
        result.setSuccess(true);
        return result;
    }

    @Override
    public List<ScenesButtonRel> queryForList(ScenesButtonRel scenesButtonRel) {
        return scenesButtonRelDao.queryForList(scenesButtonRel);
    }

    @Override
    public Result update(ScensbuttonRelBO scensbuttonRelBO) {
        Result result = new Result();
        if (null == scensbuttonRelBO || null == scensbuttonRelBO.getCataId()) {
            result.setSuccess(false);
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }

        //修改相关问题，先删除在添加
        ScenesButtonRel condition = new ScenesButtonRel();
        condition.setRelId(scensbuttonRelBO.getCataId());
        condition.setType(1);
        scenesButtonRelDao.delete(condition);

        List<ScenesButtonRel> scenesButtonRelList = scensbuttonRelBO.getScenesButtonRelList();
        if(CollectionUtils.isNotEmpty(scenesButtonRelList)){
            scenesButtonRelDao.insert(scenesButtonRelList);
        }
        result.setSuccess(true);
        return result;
    }
}
