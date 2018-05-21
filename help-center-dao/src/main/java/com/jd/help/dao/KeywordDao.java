package com.jd.help.dao;

import com.jd.help.domain.Keyword;
import com.jd.help.domain.KeywordBO;

import java.util.List;

/**
 * @author haoming1
 * @Description: 关键词对象持久层
 * @Date Created in 20:42 2018/1/8
 * @Modified By:
 */

public interface KeywordDao {

    /**
     * 保存关键词
     * @param keyword
     */
    public void insert(Keyword keyword);

    /**
     * 查询关键词列表
     * @param bo
     * @return
     */
    public List listKeyword(KeywordBO bo);


    /**
     * 查询今日插入关键词总量
     * @param bo
     * @return
     */
    Long countTodayKeyword(KeywordBO bo);
}
