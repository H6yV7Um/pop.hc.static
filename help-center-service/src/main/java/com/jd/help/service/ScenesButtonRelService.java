package com.jd.help.service;

import com.jd.common.web.result.Result;
import com.jd.help.domain.ScenesButtonRel;
import com.jd.help.domain.ScensbuttonRelBO;

import java.util.List;

/**
 * Created by lining7 on 2017/10/10.
 */
public interface ScenesButtonRelService {
    Result insert(List<ScenesButtonRel> scenesButtonRelList);

    List<ScenesButtonRel> queryForList(ScenesButtonRel scenesButtonRel);

    Result update(ScensbuttonRelBO scensbuttonRelBO);
}
