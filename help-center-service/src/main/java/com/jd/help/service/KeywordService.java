package com.jd.help.service;

import com.jd.help.domain.Keyword;
import com.jd.help.domain.KeywordBO;

import java.util.List;

/**
 * @author haoming1
 * @Description:
 * @Date Created in 20:34 2018/1/9
 * @Modified By:
 */
public interface KeywordService {

    public List<Keyword> listKeyword(KeywordBO bo);

    Long countTodayKeyword();
}
