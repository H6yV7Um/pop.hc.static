package com.jd.help.jmq.producer;

import com.jd.help.domain.Keyword;

/**
 * @author haoming1
 * @Description: ����ؼ���jmq������
 * @Date Created in 15:14 2018/1/8
 * @Modified By:
 */
public interface saveKeywordProducer {

    public void saveKeyword(Keyword keywordDto);

}
