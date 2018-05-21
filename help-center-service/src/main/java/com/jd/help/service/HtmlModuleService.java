package com.jd.help.service;

import com.jd.common.web.result.Result;
import com.jd.help.domain.HtmlModule;

import java.util.List;

public interface HtmlModuleService {
	Result list(HtmlModule htmlModule, int page, int pageSize);

    Result detail(HtmlModule htmlModule);

    Result insert(HtmlModule htmlModule);

    Result update(HtmlModule htmlModule);

    Result delete(HtmlModule htmlModule);

    List<HtmlModule> findByKeies(String[] moduleNames,int siteId);
}
