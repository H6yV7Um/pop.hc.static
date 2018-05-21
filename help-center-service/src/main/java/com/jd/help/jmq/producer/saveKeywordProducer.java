package com.jd.help.jmq.producer;

import com.jd.help.domain.Keyword;

/**
 * @author haoming1
 * @Description: 保存关键词jmq生产者
 * @Date Created in 15:14 2018/1/8
 * @Modified By:
 */
public interface saveKeywordProducer {

    public void saveKeyword(Keyword keywordDto);

}
